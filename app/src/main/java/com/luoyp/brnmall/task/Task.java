package com.luoyp.brnmall.task;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public abstract class Task<P, R> implements Runnable, Observer, TaskAction<P, R> {


    /**
     * 单例 可以提高性能
     */
    protected final static Exception withoutException = new Exception(
            "The state is without");
    // 名字映射
    private static HashMap<String, Task> nameTasks;
    /**
     * 任务编号标示
     */
    public int taskID = -1;
    /**
     * 请求参数
     */
    protected P parameter;
    /**
     * 任务开始监听
     */
    protected OnStartListen onStartListen;
    /**
     * 任务进度监听
     */
    protected OnProgressListen onProgressListen;
    /**
     * 任务完成监听
     */
    protected OnFinishListen<P, R> onFinishListen;
    /**
     * 任务在队列中完成 监听
     */
    protected OnSystemStartListen onSystemStartListen;
    /**
     * 任务在队列中开始 监听
     */
    protected OnSystemFinishListen onSystemFinishListen;
    /**
     * 结果
     */
    protected R result;
    /**
     * 设置此任务名是否为单例，单例模式下，如果相同名字的任务未执行完，则无法添加新任务
     */
    protected String singletonName;
    /**
     * 保存一个对象
     */
    protected Object tag;
    /**
     * 获得当前自身线程的引用 在threadRun方法
     */
    protected Thread thread;
    /**
     * 重连次数
     */
    protected int tryAgainCount = 1;
    /**
     * 重连间隔
     */
    protected int tryAgainTime = 1000;
    /**
     * 默认优先级低
     */
    protected TaskPriority priority = TaskPriority.min;
    protected HashMap<String, Object> dataMap;
    /**
     * 状态
     */
    TaskStatus status = TaskStatus.untreated;

    /**
     * 用于任务完成后发送消息
     */
    protected Task() {
    }

    //设置最大任务数
    public static void setThreadMaxNum(int num) {
        TaskQueue.ThreadMaxNum = num < 1 ? 1 : num > 100 ? 100 : num;
    }

    /**
     * 任务名字标示
     */

    public static HashMap<String, Task> getNameTask() {
        if (nameTasks == null) {
            nameTasks = new HashMap<String, Task>();
        }
        return nameTasks;

    }

    public String getSingletonName() {
        return singletonName;
    }

    public Task<P, R> setSingletonName(String singletonName) {
        this.singletonName = singletonName;
        return this;
    }

    public void setWithout() {
        this.status = TaskStatus.without;
    }

    public void remove() {
        this.status = TaskStatus.without;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    /**
     * 启动线程
     */
    public void start() {
        if (this.priority == null)
            this.priority = TaskPriority.min;

        synchronized (TaskQueue.tasks_wait) {
            if (getSingletonName() != null
                    && Task.getNameTask().get(this.getSingletonName()) != null) {
                this.setWithout();
            } else {
                Task.getNameTask().put(this.getSingletonName(), this);

            }

            switch (priority) {
                case min:
                    TaskQueue.tasks_wait.remove(this);
                    TaskQueue.tasks_wait.add(this);
                    break;
                case max:
                    TaskQueue.tasks_wait.remove(this);
                    TaskQueue.tasks_wait.addFirst(this);
                    break;
                default:
                    break;
            }
            // 启动此服务
            TaskQueue.serivesRun();
        }

    }

    /**
     * 启动线程
     */
    public void start(TaskPriority priority) {


        this.priority = priority;
        status = TaskStatus.wait;
        start();
    }

    /**
     * 启动线程
     */
    final void threadRun() {
        thread = new Thread(this);
        thread.start();
    }

    // 中断Execute方法
    public void shutDownExecute() {
    }

    public R cacheData(P parameter) {
        return result;
    }

    // 禁止被重写
    public final Object Execute() throws Exception {
        // TODO Auto-generated method stub
        if (onStartListen != null)
            onStartListen.onStart(this);

        // 队列中回调
        if (onSystemStartListen != null)
            onSystemStartListen.onSystemStart(this);
        // 状态从未处理改变为处理中
        status = TaskStatus.running;

        // 获取最后一次是否错误
        Exception exception = null;
        // 是否有缓存数据如果没有
        if ((result = cacheData(parameter)) == null) {

            // 失败重联次数
            for (int i = 0; i < tryAgainCount; i++) {
                try {
                    // 如果状态改变为排除则跳出失败重联
                    if (status == TaskStatus.without) {
                        break;
                    }
                    exception = null;
                    result = obtainData(this, parameter);
                    System.out.println("result=" + result);
                    break;
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    if ((exception = e) == withoutException) {
                        break;
                    }
                    e.printStackTrace();
                    try {
                        Thread.sleep(tryAgainTime);
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        }
        // 如果最后一次仍然失败则抛出
        if (exception != null) {
            throw exception;
        }


        // 如果状态改变为处理完但不通知
        if (status != TaskStatus.without) {

            if (onFinishListen != null) {
                //完成监听并将结果加入到主线程
                onFinishListen.onFinish(this, result);
            }
            ;


        }
        if (onSystemFinishListen != null) {
            onSystemFinishListen.OnSystemFinish(this, result);
        }
        status = TaskStatus.finsh;
        return result;
    }

    public abstract R obtainData(Task<P, R> task, P parameter) throws Exception;

    @Override
    public void update(Observable observable, Object data) {
        // 移除观察
        observable.deleteObserver(this);
        // 中断 停止关闭连接
        this.shutDownExecute();
        this.setWithout();
        if (this.thread != null) {
            this.thread.interrupt();
        }
        // 错误尝试次数为0
        this.tryAgainCount = 0;
    }

    @Override
    public void run() {

        try {
            Execute();
        } catch (Exception e) {
            e.printStackTrace();
            status = TaskStatus.error;


            // 如果状态改变为处理完但不通知
            if (status != TaskStatus.without) {

                if (onFinishListen != null) {
                    //将结果加入到主线程
                    onFinishListen.onFinish(this, result);
                }

            }
            if (onSystemFinishListen != null) {
                onSystemFinishListen.OnSystemFinish(this, e);
            }
        }

        //递归 避免新开线程   唤醒等待中的任务
        TaskQueue.getRunnable().notifyWaitingTask();

    }

    public Object getTag() {
        return tag;
    }

    public Task setTag(Object tag) {
        this.tag = tag;
        return this;
    }

    ;

    public Thread getThread() {
        return thread;
    }

    ;

    public TaskStatus getStatus() {
        return status;
    }

    public Object getParameter() {
        return parameter;
    }

    public Task setParameter(P parameter) {
        this.parameter = parameter;
        return this;
    }

    ;

    public OnStartListen getOnStartListen() {
        return onStartListen;
    }

//	public void cacheClear() {
//
//		if (taskOverCache) {
//			if (parameter != null)
//				parameter = null;
//			if (onStartListen != null)
//				onStartListen = null;
//			if (onProgressListen != null)
//				onProgressListen = null;
//			if (onFinishListen != null)
//				onFinishListen = null;
//			if (onSystemStartListen != null)
//				onSystemStartListen = null;
//			if (onSystemFinishListen != null)
//				onSystemFinishListen = null;
//			if (handler != null)
//				handler = null;
//			if (result != null)
//				result = null;
//			if (singletonName != null)
//				singletonName = null;
//			if (tag != null)
//				tag = null;
//			if (thread != null)
//				thread = null;
//			if (taskViewHolder != null)
//				taskViewHolder = null;
//			if (taskObservable != null)
//				taskObservable = null;
//			if (context != null)
//				context = null;
//			if (priority != null)
//				priority = null;
//		}
//	}

    public Task setOnStartListen(OnStartListen onStartListen) {
        this.onStartListen = onStartListen;
        return this;
    }

    public OnProgressListen getOnProgressListen() {
        return onProgressListen;
    }

    public Task setOnProgressListen(OnProgressListen onProgressListen) {
        this.onProgressListen = onProgressListen;
        return this;
    }

    public OnFinishListen getOnFinishListen() {
        return onFinishListen;
    }

    public Task setOnFinishListen(OnFinishListen onFinishListen) {
        this.onFinishListen = onFinishListen;
        return this;
    }

    public OnSystemStartListen getOnSystemStartListen() {
        return onSystemStartListen;
    }

    public OnSystemFinishListen getOnSystemFinishListen() {
        return onSystemFinishListen;
    }

    public void setOnSystemFinishListen(
            OnSystemFinishListen onSystemFinishListen) {
        this.onSystemFinishListen = onSystemFinishListen;
    }

    public int getTaskID() {
        return taskID;
    }

    public Task setTaskID(int taskID) {
        this.taskID = taskID;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public int getTryAgainCount() {
        return tryAgainCount;
    }

    public Task setTryAgainCount(int tryAgainCount) {
        this.tryAgainCount = tryAgainCount;
        return this;
    }

    public int getTryAgainTime() {
        return tryAgainTime;
    }

    private Task setTryAgainTime(int tryAgainTime) {
        this.tryAgainTime = tryAgainTime;
        return this;
    }

    public Object put(String key, Object value) {
        if (dataMap == null) {
            dataMap = new HashMap<String, Object>();
        }
        return dataMap.put(key, value);
    }

    public Object get(String key, Object value) {
        if (dataMap == null) {
            dataMap = new HashMap<String, Object>();
        }
        return dataMap.get(key);
    }

    public static enum TaskPriority {
        max, min;
    }

    // 任务状态
    public static enum TaskStatus {
        // 未处理 出错 完成 执行中 排除
        untreated, wait, error, finsh, running, without;
    }

    public interface OnStartListen {
        void onStart(Task t);
    }

    public interface OnProgressListen {
        void onProgress(Task task, int progress, Object data);
    }

    public static interface OnFinishListen<P, R> {
        void onFinish(Task<P, R> task, R data);
    }


    public interface OnSystemStartListen {
        void onSystemStart(Task task);
    }

    public interface OnSystemFinishListen {
        void OnSystemFinish(Task t, Object data);
    }


}
