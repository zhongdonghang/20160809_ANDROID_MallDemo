package com.luoyp.brnmall.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.adapter.MyFavoriteAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.MyFavoriteModel;
import com.luoyp.brnmall.model.UserModel;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFavoriteFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private PullToRefreshListView listView;
    private List<Object> mList;
    private MyFavoriteAdapter adapter;
    private String uid;

    public MyFavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment MyFavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFavoriteFragment newInstance(String param1) {
        MyFavoriteFragment fragment = new MyFavoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_my_favorite, container, false);
        listView = (PullToRefreshListView) rootView.findViewById(R.id.listview);

        mList = new ArrayList<>();
        adapter = new MyFavoriteAdapter(getActivity(),mList,mParam1);
        listView.setAdapter(adapter);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                mList.clear();
                if (mParam1.equals("1")){
                    getGoodsData(uid);
                } else if (mParam1.equals("2")){
                    getStoreData(uid);
                }
            }
        });
        // 获取当前用户的uid
        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        uid = userModel.getUserInfo().getUid() + "";
        if (mParam1.equals("1")){
            getGoodsData(uid);
        } else if (mParam1.equals("2")){
            getStoreData(uid);
        }
        return rootView;
    }

    private void getGoodsData(String uid){
        showProgressDialog("");
        BrnmallAPI.favoriteProductList(uid, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                listView.onRefreshComplete();
            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                listView.onRefreshComplete();
                KLog.json("收藏的商品   "+response);

                if (TextUtils.isEmpty(response)){
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("result").equals("false")){

                    } else {
                        mList.addAll(MyFavoriteModel.jsonToGoodsBeanList(jsonObject.getString("data")));
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getStoreData(String uid){
        showProgressDialog("");
        BrnmallAPI.favoriteStoreList(uid, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                listView.onRefreshComplete();
            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                listView.onRefreshComplete();
                KLog.json("收藏的店铺   "+response);

                if (TextUtils.isEmpty(response)){
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("result").equals("false")){

                    } else {
                        mList.addAll(MyFavoriteModel.jsonToStoreBeanList(jsonObject.getString("data")));
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
