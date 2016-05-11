package com.luoyp.brnmall.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.internal.Utils;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.adapter.HomeGoodsAdapter;
import com.luoyp.brnmall.adapter.ImagePagerAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.HomeGoods;
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

    private List<String> imageIdList;
    private List<HomeGoods> homeGoodsList;
    private HomeGoodsAdapter adapter;
    private ImagePagerAdapter homeAdAdapter;


    private com.luoyp.brnmall.view.AutoScrollViewPager autoviewpager;
    private com.handmark.pulltorefresh.library.PullToRefreshListView homelistview;
    private android.support.v4.widget.SwipeRefreshLayout swipemessage;

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

        this.swipemessage = (SwipeRefreshLayout) view.findViewById(R.id.swipe_message);
        this.homelistview = (PullToRefreshListView) view.findViewById(R.id.home_list_view);
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

        homelistview.setMode(PullToRefreshBase.Mode.DISABLED);
        homelistview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //   getHomeGoods();
//                homelistview.onRefreshComplete();
//                homelistview.setRefreshing(false);
            }
        });

        homelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KLog.d(position);

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
                        for (int i = 0; i < json.getJSONArray("data").length(); i++) {
                            HomeGoods goods = new HomeGoods();
                            goods.setPname(json.getJSONArray("data").getJSONObject(i).getString("Title"));
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
}
