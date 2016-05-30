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
import android.widget.TextView;

import com.baidu.autoupdatesdk.AppUpdateInfo;
import com.baidu.autoupdatesdk.AppUpdateInfoForInstall;
import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.CPCheckUpdateCallback;
import com.baidu.autoupdatesdk.CPUpdateDownloadCallback;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.internal.Utils;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.activity.GoodsDetailActivity;
import com.luoyp.brnmall.adapter.HomeGoodsAdapter;
import com.luoyp.brnmall.adapter.ImagePagerAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.HomeGoods;
import com.luoyp.brnmall.utils.PermissionUtil;
import com.luoyp.brnmall.view.AutoScrollViewPager;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    AlertDialog newversionDialog = null;
    ProgressDialog progressBar;
    private List<String> imageIdList;
    private List<HomeGoods> homeGoodsList;
    private HomeGoodsAdapter adapter;
    private ImagePagerAdapter homeAdAdapter;
    private com.luoyp.brnmall.view.AutoScrollViewPager autoviewpager;
    //private com.handmark.pulltorefresh.library.PullToRefreshListView homelistview;
    private PullToRefreshGridView homelistview;
    private android.support.v4.widget.SwipeRefreshLayout swipemessage;
    private android.widget.TextView tvhot;

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
        //判断权限
        if (PermissionUtil.hasSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            BDAutoUpdateSDK.cpUpdateCheck(getActivity(), new MyCPCheckUpdateCallback());
        } else {
            PermissionUtil.requestPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        imageIdList = new ArrayList<String>();
        imageIdList.add("1");
        imageIdList.add("2");
        imageIdList.add("3");
        imageIdList.add("4");

        homeGoodsList = new ArrayList<>();
        homeAdAdapter = new ImagePagerAdapter(getActivity(), imageIdList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.tvhot = (TextView) view.findViewById(R.id.tv_hot);

        this.swipemessage = (SwipeRefreshLayout) view.findViewById(R.id.swipe_message);
        this.homelistview = (PullToRefreshGridView) view.findViewById(R.id.home_list_view);
        this.autoviewpager = (AutoScrollViewPager) view.findViewById(R.id.auto_view_pager);

        autoviewpager.setAdapter(homeAdAdapter.setInfiniteLoop(true));
        autoviewpager.setOnPageChangeListener(new MyOnPageChangeListener());
        autoviewpager.setInterval(2500);
        autoviewpager.startAutoScroll();
        autoviewpager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % Utils.getSize(imageIdList));

        swipemessage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homeGoodsList.clear();
                getHomeGoods();
                getHomeAds();
            }
        });

        homelistview.getRefreshableView().setNumColumns(2);
        homelistview.setMode(PullToRefreshBase.Mode.DISABLED);


        homelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KLog.d("产品id" + homeGoodsList.get(position).getPid());
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("pid", homeGoodsList.get(position).getPid() + "");
                intent.putExtra("name", homeGoodsList.get(position).getPname());
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
        getHomeGoods();
        return view;
    }

    public void getHomeGoods() {
        BrnmallAPI.GetAdvertList("41", new ApiCallback<String>() {
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
                        tvhot.setVisibility(View.VISIBLE);
                        for (int i = 0; i < json.getJSONArray("data").length(); i++) {
                            HomeGoods goods = new HomeGoods();
                            goods.setPname(json.getJSONArray("data").getJSONObject(i).getString("Title"));
                            goods.setPrice(json.getJSONArray("data").getJSONObject(i).getString("ExtField2"));
                            goods.setPid(json.getJSONArray("data").getJSONObject(i).getString("ExtField1"));
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
                    if (json.getJSONArray("data").length() >= 1) {
                        for (int i = 0; i < json.getJSONArray("data").length(); i++) {
                            imageIdList.set(i, BrnmallAPI.adImgUrl + json.getJSONArray("data").getJSONObject(i).getString("Body"));
                        }
                        homeAdAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
