package com.luoyp.brnmall.task;

public interface TaskAction<P, R> {
    public R obtainData(Task<P, R> task, P parameter) throws Exception;
}