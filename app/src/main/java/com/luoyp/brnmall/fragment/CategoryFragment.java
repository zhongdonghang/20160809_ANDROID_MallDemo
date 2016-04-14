package com.luoyp.brnmall.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.adapter.CategoryAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.CategoryModel;
import com.luoyp.xlibrary.tools.TLog;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseFragment {


    private ListView categorylistview;
    private PullToRefreshListView categorygoodslistview;

    private CategoryAdapter adapter;
    private List<CategoryModel.Category> categoryListData;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        this.categorygoodslistview = (PullToRefreshListView) view.findViewById(R.id.category_goods_list_view);
        this.categorylistview = (ListView) view.findViewById(R.id.category_list_view);
        setupView(view);
        return view;
    }

    public void setupView(View views) {

//        categorylistview.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
//        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                getCommunityInfoList();
//            }
//        });
        categorylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TLog.d(position + "");

//                Intent intent = new Intent(getActivity(), MessageDetailActivity_.class);
//                //position从1开始,所以list中对应减1
//                intent.putExtra("id", messageListData.get(position - 1).getId());
//                startActivity(intent);
            }
        });

        categoryListData = new ArrayList<>();
        adapter = new CategoryAdapter(getActivity(), categoryListData);
        categorylistview.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog("正在加载数据");
        getCategoty();
    }

    public void getCategoty() {
        BrnmallAPI.getCategory("", new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                TLog.d("一级目录：" + response);
                CategoryModel category = new Gson().fromJson(response, CategoryModel.class);
                categoryListData = category.getCategory();
                adapter.notifyDataSetChanged();
                //   categorylistview.invalidate();
            }
        });
    }
}
