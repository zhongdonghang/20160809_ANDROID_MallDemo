package com.luoyp.brnmall.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.activity.BrandGoodsActivity;
import com.luoyp.brnmall.adapter.BrandAdapter;
import com.luoyp.brnmall.adapter.CategoryAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.Brand;
import com.luoyp.brnmall.model.CategoryModel;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BrandFragment extends BaseFragment {

    private android.widget.ImageButton emptybtn;
    private android.widget.ListView brandcategorylistview;
    private com.handmark.pulltorefresh.library.PullToRefreshGridView brandlistview;
    private CategoryAdapter adapter;
    private List<CategoryModel.Category> categoryListData;

    private BrandAdapter brandAdapter;
    private List<Brand> brandListData;

    private boolean isFirstLoad = true;  // 首次可见标志
    private String curCateID = "";
    private android.support.v4.widget.SwipeRefreshLayout swipemessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_brand, container, false);
        this.swipemessage = (SwipeRefreshLayout) v.findViewById(R.id.swipe_message);
        this.brandlistview = (PullToRefreshGridView) v.findViewById(R.id.brand_list_view);
        this.brandcategorylistview = (ListView) v.findViewById(R.id.brandcategory_list_view);
        this.emptybtn = (ImageButton) v.findViewById(R.id.emptybtn);

        categoryListData = new ArrayList<>();
        adapter = new CategoryAdapter(getActivity(), categoryListData);
        brandcategorylistview.setAdapter(adapter);
        brandlistview.setMode(PullToRefreshBase.Mode.DISABLED);
        brandcategorylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KLog.d(position + "  " + id);
                adapter.setMySelection(position);

                brandListData.clear();
                curCateID = categoryListData.get(position).getCateId() + "";
                getBrandList(categoryListData.get(position).getCateId() + "");
            }
        });

        swipemessage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                brandListData.clear();
                getBrandList(curCateID);
            }
        });

        brandlistview.getRefreshableView().setNumColumns(2);
        brandListData = new ArrayList<>();
        brandAdapter = new BrandAdapter(getActivity(), brandListData);
        brandlistview.setAdapter(brandAdapter);

        brandlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KLog.d(position);
                Intent intent = new Intent(getActivity(), BrandGoodsActivity.class);
                intent.putExtra("cateId", curCateID);
                intent.putExtra("brandId", brandListData.get(position).getBrandId());
                intent.putExtra("name", brandListData.get(position).getBrandName());
                startActivity(intent);

            }
        });

        //解决listview无法滚动问题
        brandlistview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (brandListData == null || brandlistview.getChildCount() == 0) ?
                                0 : brandlistview.getChildAt(0).getTop();
                swipemessage.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });
        emptybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog("正在加载数据");
                categoryListData.clear();
                getCategoty();
            }
        });

        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirstLoad) {
            showProgressDialog("正在加载数据");
            categoryListData.clear();

            getCategoty();
        }
    }

    public void getBrandList(String id) {

        BrnmallAPI.GetCateBrandList(id, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                swipemessage.setRefreshing(false);
                brandAdapter.notifyDataSetChanged();
            }

            @Override
            public void onResponse(String response) {
                swipemessage.setRefreshing(false);
                if (response == null || TextUtils.isEmpty(response)) {
                    brandAdapter.notifyDataSetChanged();
                    showToast("暂时没有相关品牌");
                    return;
                }
                try {
                    JSONObject json = new JSONObject(response);
                    int length = json.getJSONObject("data").getJSONArray("BrandList").length();
                    if (length >= 1) {
                        for (int i = 0; i < length; i++) {
                            Brand brand = new Brand();

                            brand.setBrandId(json.getJSONObject("data").getJSONArray("BrandList").getJSONObject(i).getString("BrandId"));
                            brand.setBrandImg(BrnmallAPI.brandImgUrl + json.getJSONObject("data").getJSONArray("BrandList").getJSONObject(i).getString("Logo"));
                            brand.setBrandName(json.getJSONObject("data").getJSONArray("BrandList").getJSONObject(i).getString("Name"));
                            brandListData.add(brand);
                        }
                        brandAdapter.notifyDataSetChanged();
                    } else {
                        showToast("暂时没有相关品牌");
                        brandAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getCategoty() {
        emptybtn.setVisibility(View.GONE);
        BrnmallAPI.getCategory("", new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                brandcategorylistview.setVisibility(View.GONE);
                emptybtn.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                KLog.json("一级目录：" + response);
                if (response == null) {
                    return;
                }
                CategoryModel category = new Gson().fromJson(response, CategoryModel.class);

                if (category.getResult() == "false") {
                    brandcategorylistview.setVisibility(View.GONE);
                    emptybtn.setVisibility(View.VISIBLE);
                    return;
                }
                if (emptybtn.isShown()) {
                    emptybtn.setVisibility(View.GONE);
                }
                if (!brandcategorylistview.isShown()) {
                    brandcategorylistview.setVisibility(View.VISIBLE);
                }
                categoryListData.addAll(category.getCategory());

                adapter.notifyDataSetChanged();
                brandcategorylistview.performItemClick(brandcategorylistview, 0, 0);
                adapter.setMySelection(0);
                isFirstLoad = false;
            }
        });
    }
}
