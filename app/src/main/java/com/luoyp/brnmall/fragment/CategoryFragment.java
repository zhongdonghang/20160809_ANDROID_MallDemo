package com.luoyp.brnmall.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.activity.GoodsDetailActivity;
import com.luoyp.brnmall.adapter.CategoryAdapter;
import com.luoyp.brnmall.adapter.CategoryGoodsAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.CategoryGoodsModel;
import com.luoyp.brnmall.model.CategoryModel;
import com.luoyp.brnmall.model.UserModel;
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
public class CategoryFragment extends BaseFragment {


    ImageButton refreshBtn;
    private ListView categorylistview;
    private PullToRefreshListView categorygoodslistview;
    private CategoryAdapter adapter;
    private List<CategoryModel.Category> categoryListData;
    private CategoryGoodsModel categoryGoodsModel;
    private CategoryGoodsAdapter categoryGoodsAdapter;

    private boolean isFirstLoad = true;  // 首次可见标志
    private int pageNumber = 1;
    private String cateId;

//    public CategoryFragment() {
//        // Required empty public constructor
//    }
//
//    public static CategoryFragment newInstance(String param1) {
//        CategoryFragment fragment = new CategoryFragment();
//        Bundle args = new Bundle();
//        args.putString("agrs1", param1);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        // 实例化
        categoryGoodsModel = new CategoryGoodsModel();
        categoryGoodsModel.setGoodsBeanList(new ArrayList<CategoryGoodsModel.GoodsBean>());
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        this.categorygoodslistview = (PullToRefreshListView) view.findViewById(R.id.category_goods_list_view);
        this.categorylistview = (ListView) view.findViewById(R.id.category_list_view);
        refreshBtn = (ImageButton) view.findViewById(R.id.emptybtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog("正在加载数据");
                categoryListData.clear();
                adapter.notifyDataSetChanged();
                getCategoty();
            }
        });
        categoryListData = new ArrayList<>();
        adapter = new CategoryAdapter(getActivity(), categoryListData);
        categorylistview.setAdapter(adapter);

        categorylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KLog.d(position + "  " + id);
                adapter.setMySelection(position);
                pageNumber = 1;
                cateId = categoryListData.get(position).getCateId() + "";
                categoryGoodsModel.getGoodsBeanList().clear();
                categoryGoodsAdapter.notifyDataSetChanged();
                getCategoryCoodsList(cateId, pageNumber + "");
//                Intent intent = new Intent(getActivity(), MessageDetailActivity_.class);
//                //position从1开始,所以list中对应减1
//                intent.putExtra("id", messageListData.get(position - 1).getId());
//                startActivity(intent);
            }
        });
        setupListView();
        return view;
    }

    public void setupView(View views) {

    }

    private void setupListView() {
        // 设置适配器
        categoryGoodsAdapter = new CategoryGoodsAdapter(getActivity(), categoryGoodsModel);
        categorygoodslistview.setAdapter(categoryGoodsAdapter);
        // 设置刷新方式
        categorygoodslistview.setMode(PullToRefreshBase.Mode.BOTH);
        categorygoodslistview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉
                pageNumber = 1;
                categoryGoodsModel.getGoodsBeanList().clear();
                getCategoryCoodsList(cateId, pageNumber + "");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getCategoryCoodsList(cateId, pageNumber + "");
            }
        });
        categorygoodslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KLog.d("产品id" + categoryGoodsModel.getGoodsBeanList().get(position - 1).getPid());
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("pid", categoryGoodsModel.getGoodsBeanList().get(position - 1).getPid() + "");
                intent.putExtra("name", categoryGoodsModel.getGoodsBeanList().get(position - 1).getName());
                startActivity(intent);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirstLoad) {
            showProgressDialog("正在加载数据");
            categoryListData.clear();
            adapter.notifyDataSetChanged();
            getCategoty();
            return;
        }
        if (!isFirstLoad) {
            if ("33".equals(App.cateIndex)) {
                categorylistview.performItemClick(categorylistview, 5, 5);
                adapter.setMySelection(5);
                isFirstLoad = false;
                App.cateIndex = "-1";
                return;
            }
            if ("34".equals(App.cateIndex)) {
                categorylistview.performItemClick(categorylistview, 0, 0);
                adapter.setMySelection(0);
                isFirstLoad = false;
                App.cateIndex = "-1";
                return;
            }

            if ("35".equals(App.cateIndex)) {
                categorylistview.performItemClick(categorylistview, 1, 1);
                adapter.setMySelection(1);
                isFirstLoad = false;
                App.cateIndex = "-1";
                return;
            }
            if ("36".equals(App.cateIndex)) {
                categorylistview.performItemClick(categorylistview, 2, 2);
                adapter.setMySelection(2);
                isFirstLoad = false;
                App.cateIndex = "-1";
                return;
            }
            if ("37".equals(App.cateIndex)) {
                categorylistview.performItemClick(categorylistview, 3, 3);
                adapter.setMySelection(3);
                isFirstLoad = false;
                App.cateIndex = "-1";
                return;
            }
            if ("38".equals(App.cateIndex)) {
                categorylistview.performItemClick(categorylistview, 4, 4);
                adapter.setMySelection(4);
                isFirstLoad = false;
                App.cateIndex = "-1";
                return;
            }
            if ("39".equals(App.cateIndex)) {
                categorylistview.performItemClick(categorylistview, 6, 6);
                adapter.setMySelection(6);
                isFirstLoad = false;
                App.cateIndex = "-1";
                return;
            }
            categorylistview.performItemClick(categorylistview, 0, 0);
            adapter.setMySelection(0);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void getCategoty() {
        refreshBtn.setVisibility(View.GONE);
        BrnmallAPI.getCategory("", new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                categorylistview.setVisibility(View.GONE);
                refreshBtn.setVisibility(View.VISIBLE);
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
                    categorylistview.setVisibility(View.GONE);
                    refreshBtn.setVisibility(View.VISIBLE);
                    return;
                }
                if (refreshBtn.isShown()) {
                    refreshBtn.setVisibility(View.GONE);
                }
                if (!categorylistview.isShown()) {
                    categorylistview.setVisibility(View.VISIBLE);
                }
                categoryListData.addAll(category.getCategory());

                adapter.notifyDataSetChanged();
                if (isFirstLoad) {
                    if ("33".equals(App.cateIndex)) {
                        categorylistview.performItemClick(categorylistview, 5, 5);
                        adapter.setMySelection(5);
                        isFirstLoad = false;
                        App.cateIndex = "-1";
                        return;
                    }
                    if ("34".equals(App.cateIndex)) {
                        categorylistview.performItemClick(categorylistview, 0, 0);
                        adapter.setMySelection(0);
                        isFirstLoad = false;
                        App.cateIndex = "-1";
                        return;
                    }

                    if ("35".equals(App.cateIndex)) {
                        categorylistview.performItemClick(categorylistview, 1, 1);
                        adapter.setMySelection(1);
                        isFirstLoad = false;
                        App.cateIndex = "-1";
                        return;
                    }
                    if ("36".equals(App.cateIndex)) {
                        categorylistview.performItemClick(categorylistview, 2, 2);
                        adapter.setMySelection(2);
                        isFirstLoad = false;
                        App.cateIndex = "-1";
                        return;
                    }
                    if ("37".equals(App.cateIndex)) {
                        categorylistview.performItemClick(categorylistview, 3, 3);
                        adapter.setMySelection(3);
                        isFirstLoad = false;
                        App.cateIndex = "-1";
                        return;
                    }
                    if ("38".equals(App.cateIndex)) {
                        categorylistview.performItemClick(categorylistview, 4, 4);
                        adapter.setMySelection(4);
                        isFirstLoad = false;
                        App.cateIndex = "-1";
                        return;
                    }
                    if ("39".equals(App.cateIndex)) {
                        categorylistview.performItemClick(categorylistview, 6, 6);
                        adapter.setMySelection(6);
                        isFirstLoad = false;
                        App.cateIndex = "-1";
                        return;
                    }
                    categorylistview.performItemClick(categorylistview, 0, 0);
                    adapter.setMySelection(0);
                }
                isFirstLoad = false;
            }
        });
    }

    /**
     * 获取分类商品
     *
     * @param cateId    分类id
     * @param pageIndex 页码
     */
    private void getCategoryCoodsList(String cateId, String pageIndex) {
        BrnmallAPI.getProductListByCateId(cateId, pageIndex, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                // 关闭加载提示
                showToast("网络异常,请稍后再试吧");
                dismissProgressDialog();
                categorygoodslistview.onRefreshComplete();

            }

            @Override
            public void onResponse(String response) {
                // 关闭加载提示
                dismissProgressDialog();
                categorygoodslistview.onRefreshComplete();

                KLog.json("分类商品=  " + response);
                if (TextUtils.isEmpty(response)) {
                    showToast("没有找到相关商品");
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    if (jsonObject.getString("result").equals("false") || dataObject.getJSONArray("ProductList").length() == 0) {
                        showToast("没有商品了");
                        return;
                    }

                    categoryGoodsModel.setPageInfo(categoryGoodsModel.jsonToPageInfoBean(dataObject.getString("PageModel")));
                    categoryGoodsModel.getGoodsBeanList().addAll(categoryGoodsModel.jsonToGoodsBeanList(dataObject.getString("ProductList")));
                    categoryGoodsAdapter.notifyDataSetChanged();
                    pageNumber++;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Subscriber(tag = "add_tocart")
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
}
