package com.luoyp.brnmall.fragment;


import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.autoupdatesdk.AppUpdateInfo;
import com.baidu.autoupdatesdk.AppUpdateInfoForInstall;
import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.CPCheckUpdateCallback;
import com.baidu.autoupdatesdk.CPUpdateDownloadCallback;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.internal.Utils;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.activity.GoodsDetailActivity;
import com.luoyp.brnmall.activity.SearchActivity;
import com.luoyp.brnmall.adapter.HomeGoodsAdapter;
import com.luoyp.brnmall.adapter.ImagePagerAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.HomeGoods;
import com.luoyp.brnmall.model.UserModel;
import com.luoyp.brnmall.task.Task;
import com.luoyp.brnmall.utils.PermissionUtil;
import com.luoyp.brnmall.view.AutoScrollViewPager;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    AlertDialog newversionDialog = null;
    ProgressDialog progressBar;
    private List<String> imageIdList;
    private List<String> adIdList;
    private List<HomeGoods> homeGoodsList;
    private List<HomeGoods> homeGoodsList1;
    private List<HomeGoods> homeGoodsList2;
    private List<HomeGoods> homeGoodsList3;
    private List<HomeGoods> homeGoodsList4;
    private List<HomeGoods> homeGoodsList5;
    private List<HomeGoods> homeGoodsList6;
    private List<HomeGoods> homeGoodsList7;
    private HomeGoodsAdapter adapter;
    private ImagePagerAdapter homeAdAdapter;
    private com.luoyp.brnmall.view.AutoScrollViewPager autoviewpager;
    private com.handmark.pulltorefresh.library.PullToRefreshListView homelistview;
    //    private PullToRefreshGridView homelistview;
    private android.support.v4.widget.SwipeRefreshLayout swipemessage;
    private android.widget.ImageView llogo;
    private android.widget.RelativeLayout searbar;
    private android.widget.LinearLayout tvhot;
    private RelativeLayout homehead;
    private ImageView searchIv;
    private android.widget.EditText searchEt;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        //判断权限
        if (PermissionUtil.hasSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            BDAutoUpdateSDK.cpUpdateCheck(getActivity(), new MyCPCheckUpdateCallback());
        } else {
            PermissionUtil.requestPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        imageIdList = new ArrayList<String>();
        imageIdList.add("1");
        imageIdList.add("2");
        imageIdList.add("3");
        imageIdList.add("4");
        adIdList = new ArrayList<>();
        adIdList.add("1");
        adIdList.add("2");
        adIdList.add("3");
        adIdList.add("4");

        homeGoodsList = new ArrayList<>();
        homeGoodsList1 = new ArrayList<>();
        homeGoodsList2 = new ArrayList<>();
        homeGoodsList3 = new ArrayList<>();
        homeGoodsList4 = new ArrayList<>();
        homeGoodsList5 = new ArrayList<>();
        homeGoodsList6 = new ArrayList<>();
        homeGoodsList7 = new ArrayList<>();
        homeAdAdapter = new ImagePagerAdapter(getActivity(), imageIdList, true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.searchEt = (EditText) view.findViewById(R.id.searchEt);
        this.searchIv = (ImageView) view.findViewById(R.id.searchIv);
        this.homehead = (RelativeLayout) view.findViewById(R.id.homehead);
        this.tvhot = (LinearLayout) view.findViewById(R.id.tv_hot);
        this.searbar = (RelativeLayout) view.findViewById(R.id.searbar);
        this.llogo = (ImageView) view.findViewById(R.id.llogo);

        this.swipemessage = (SwipeRefreshLayout) view.findViewById(R.id.swipe_message);
        this.homelistview = (PullToRefreshListView) view.findViewById(R.id.home_list_view);
        // this.autoviewpager = (AutoScrollViewPager) header.findViewById(R.id.auto_view_pager);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);

        View header = inflater.inflate(R.layout.homehead, container, false);
        header.setLayoutParams(layoutParams);
        this.autoviewpager = (AutoScrollViewPager) header.findViewById(R.id.auto_view_pager);
        autoviewpager.setAdapter(homeAdAdapter.setInfiniteLoop(true));
        autoviewpager.setOnPageChangeListener(new MyOnPageChangeListener());
        autoviewpager.setInterval(2500);
        autoviewpager.startAutoScroll();
        autoviewpager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % Utils.getSize(imageIdList));

        swipemessage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homeGoodsList.clear();
                doGetHomeGoodsTask();
                getHomeAds();
            }
        });

        searchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchEt.getText().toString().isEmpty()) {
                    showToast("请输入要搜索的商品名称");
                    return;
                }

                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("key", searchEt.getText().toString());
                startActivity(intent);

            }
        });
        homelistview.getRefreshableView().addHeaderView(header);
        // homelistview.getRefreshableView().setNumColumns(2);
        homelistview.setMode(PullToRefreshBase.Mode.DISABLED);


        homelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KLog.d("产品id" + homeGoodsList.get(position - 1).getPid());
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("pid", homeGoodsList.get(position - 1).getPid() + "");
                intent.putExtra("name", homeGoodsList.get(position - 1).getPname());
                startActivity(intent);
            }
        });
        //解决listview无法滚动问题
        homelistview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (homeGoodsList == null || homelistview.getChildCount() == 0) ?
                                0 : homelistview.getChildAt(0).getTop();
                swipemessage.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });

        adapter = new HomeGoodsAdapter(getActivity(), homeGoodsList);
        homelistview.setAdapter(adapter);

        getHomeAds();

        doGetHomeGoodsTask();
        return view;
    }

    @Subscriber(tag = "clickAD")
    public void clickAd(int pos) {
        KLog.d("click ad id = " + adIdList.get(pos));
        Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
        intent.putExtra("pid", adIdList.get(pos));
        intent.putExtra("name", "");
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void doGetHomeGoodsTask() {
        Task.setThreadMaxNum(5);

        for (int i = 0; i < 7; i++) {
            final int aid = 33 + i;
            KLog.d("aid= " + aid);
            new Task() {

                @Override
                public Object obtainData(Task task, Object parameter)
                        throws Exception {
                    // TODO Auto-generated method stub
                    getHomeGoods(aid + "");

                    return task.taskID;
                }

            }
                    .setOnFinishListen(new Task.OnFinishListen() {

                        @Override
                        public void onFinish(Task task, Object data) {

                            // TODO Auto-generated method stub
                            //  System.err.println("任务编号" + task.taskID + "任务完成");
                        }
                    })
                    .setTaskID(i)
                    .start();
        }
    }

    public void getHomeGoods(final String id) {
        BrnmallAPI.GetAdvertList(id, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                swipemessage.setRefreshing(false);
            }

            @Override
            public void onResponse(String response) {
                swipemessage.setRefreshing(false);
                if (response == null || TextUtils.isEmpty(response)) {
                    return;
                }
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.getJSONArray("data").length() >= 1) {
                        //   tvhot.setVisibility(View.VISIBLE);
//                        let AdId = obj["AdId"].stringValue
//                        let AdPosId = obj["AdPosId"].stringValue
//                        let Title = obj["Title"].stringValue
//                        let Url = obj["Url"].stringValue
//                        let Body = obj["Body"].stringValue
//                        let ExtField1 = obj["ExtField1"].stringValue
//                        let ExtField2 = obj["ExtField2"].stringValue
//                        let ExtField3 = obj["ExtField3"].stringValue
//                        let ExtField4 = obj["ExtField4"].stringValue
//                        let ExtField5 = obj["ExtField5"].stringValue
//                        let State = obj[""].stringValue
//                        let Type = obj[""].stringValue
//                        let img = obj["Body"].stringValue
                        for (int i = 0; i < json.getJSONArray("data").length(); i++) {
                            HomeGoods goods = new HomeGoods();
                            if ("33".equals(id) && i == 0) {
                                goods.setItemType(id);
                                homeGoodsList.add(goods);

                            }
                            if ("34".equals(id) && i == 0) {
                                goods.setItemType(id);
                                homeGoodsList.add(goods);

                            }
                            if ("35".equals(id) && i == 0) {
                                goods.setItemType(id);
                                homeGoodsList.add(goods);

                            }
                            if ("36".equals(id) && i == 0) {
                                goods.setItemType(id);
                                homeGoodsList.add(goods);

                            }
                            if ("37".equals(id) && i == 0) {
                                goods.setItemType(id);
                                homeGoodsList.add(goods);

                            }
                            if ("38".equals(id) && i == 0) {
                                goods.setItemType(id);
                                homeGoodsList.add(goods);

                            }
                            if ("39".equals(id) && i == 0) {
                                goods.setItemType(id);
                                homeGoodsList.add(goods);

                            }

                            goods = new HomeGoods();
                            goods.setState(json.getJSONArray("data").getJSONObject(i).getString("State"));
                            goods.setType(json.getJSONArray("data").getJSONObject(i).getString("Type"));
                            goods.setPname(json.getJSONArray("data").getJSONObject(i).getString("ExtField1"));
                            goods.setPrice(json.getJSONArray("data").getJSONObject(i).getString("ExtField2"));
                            goods.setMarkiprice(json.getJSONArray("data").getJSONObject(i).getString("ExtField3"));
                            goods.setPid(json.getJSONArray("data").getJSONObject(i).getString("ExtField5"));
                            goods.setImg(BrnmallAPI.adImgUrl + json.getJSONArray("data").getJSONObject(i).getString("Body"));


                            homeGoodsList.add(goods);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getHomeAds() {
        BrnmallAPI.GetAdvertList("2", new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                swipemessage.setRefreshing(false);
            }

            @Override
            public void onResponse(String response) {
                if (response == null || TextUtils.isEmpty(response)) {
                    return;
                }
                try {
                    JSONObject json = new JSONObject(response);
                    KLog.d("ad json= " + json);
                    if (json.getJSONArray("data").length() >= 1) {
                        for (int i = 0; i < 4; i++) {
                            imageIdList.set(i, BrnmallAPI.adImgUrl + json.getJSONArray("data").getJSONObject(i).getString("Body"));
                            adIdList.set(i, json.getJSONArray("data").getJSONObject(i).getString("ExtField5"));
                        }
                        homeAdAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Subscriber(tag = "home_add_tocart")
    public void addToCart(String pid) {
        KLog.d(pid);
        if (!checkLogin()) {
            return;
        }

        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        String uid = String.valueOf(userModel.getUserInfo().getUid());
        if (uid == null || uid.isEmpty()) {
            checkLogin();
        } else {
            addGoodsToCart(pid, uid, "1");
        }
    }

    /**
     * 加入购物车
     *
     * @param pid   商品id
     * @param uid   用户id
     * @param count 数量
     */
    private void addGoodsToCart(String pid, String uid, String count) {
        showProgressDialog("正在添加到购物车");
        BrnmallAPI.addProductToCart(pid, uid, count, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                showToast("网络异常,请稍后再试吧");
            }

            @Override
            public void onResponse(String response) {
                //  KLog.json(response);
                dismissProgressDialog();
                if (response != null && !TextUtils.isEmpty(response)) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        showToast(jsonObject.getJSONArray("data").getJSONObject(0).getString("msg"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
//            indexText.setText(new StringBuilder().append((position) % ListUtils.getSize(imageIdList) + 1).append("/")
//                    .append(ListUtils.getSize(imageIdList)));
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    private class MyCPCheckUpdateCallback implements CPCheckUpdateCallback {

        @Override
        public void onCheckUpdateCallback(final AppUpdateInfo info, AppUpdateInfoForInstall infoForInstall) {
            if (infoForInstall != null && !TextUtils.isEmpty(infoForInstall.getInstallPath())) {
                KLog.d("install info:" + infoForInstall.getAppSName() + ", \nverion=" + infoForInstall.getAppVersionName() + ", \nchange log=" + infoForInstall.getAppChangeLog());
                KLog.d("install path:", infoForInstall.getInstallPath());
                BDAutoUpdateSDK.cpUpdateInstall(getActivity().getApplicationContext(), infoForInstall.getInstallPath());
            } else if (info != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = getActivity().getLayoutInflater().inflate(R.layout.alert_new_version, null);
                builder.setView(view);
                final Button btnDownload = (Button) view.findViewById(R.id.downnewapk);
                final TextView log = (TextView) view.findViewById(R.id.appChangeLog);
                final TextView version = (TextView) view.findViewById(R.id.versioninfo);
                version.setText("发现新版本(" + info.getAppVersionName() + info.getAppVersionCode() + ")");
                log.setText("更新:" + "\n" + info.getAppChangeLog());
                if (newversionDialog == null) {
                    newversionDialog = builder.create();
                }
                newversionDialog.setCanceledOnTouchOutside(false);
                newversionDialog.show();
                btnDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newversionDialog.dismiss();
                        progressBar = new ProgressDialog(getActivity());
                        progressBar.setCancelable(true);
                        progressBar.setMessage("正在下载 ...");
                        progressBar.setCanceledOnTouchOutside(false);
                        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressBar.setProgress(0);
                        progressBar.setMax(100);
                        progressBar.show();
                        BDAutoUpdateSDK.cpUpdateDownload(getActivity(), info, new UpdateDownloadCallback());
                    }
                });
            } else {
                //  showToast("no update");
            }
        }
    }

    private class UpdateDownloadCallback implements CPUpdateDownloadCallback {

        @Override
        public void onDownloadComplete(String apkPath) {
            showToast("下载完成");
            progressBar.dismiss();
            BDAutoUpdateSDK.cpUpdateInstall(getActivity().getApplicationContext(), apkPath);
        }

        @Override
        public void onStart() {
            showToast("开始下载...");
        }

        @Override
        public void onPercent(int percent, long rcvLen, long fileSize) {
            KLog.d("onPercent:" + percent + " % ");
            progressBar.setProgress(percent);
        }

        @Override
        public void onFail(Throwable error, String content) {
            progressBar.dismiss();
            // showToast("下载失败，稍后再试...");
        }

        @Override
        public void onStop() {
            progressBar.dismiss();
        }

    }
}
