package com.luoyp.brnmall.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.adapter.ShopCarAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.ShopCartModel;
import com.luoyp.brnmall.model.UserModel;
import com.luoyp.xlibrary.tools.TLog;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopCarFragment extends BaseFragment {

    private SwipeRefreshLayout swipe;
    private ListView listView;
    private TextView tvSum, tvJiesuan;
    private ShopCartModel shopCartModel;
    private ShopCarAdapter adapter;
    private String uid;
    private boolean isFirstVisible = true;  // 首次可见标志
    private boolean isLogin = false;    // 登录标志

    public ShopCarFragment() {
        // Required empty public constructor

    }

    public static ShopCarFragment newInstance(String param1) {
        ShopCarFragment fragment = new ShopCarFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 实例化数据源集合
        shopCartModel = new ShopCartModel();
        shopCartModel.setCartGoodsBeanList(new ArrayList<ShopCartModel.CartGoodsBean>());
        // 注册事件的接收对象
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_car, container, false);
//        Bundle bundle = getArguments();
//        String agrs1 = bundle.getString("agrs1");
//        TextView tv = (TextView)view.findViewById(R.id.tv_location);
//        tv.setText(agrs1);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        listView = (ListView) view.findViewById(R.id.listView);
        tvSum = (TextView) view.findViewById(R.id.tv_sum);
        tvJiesuan = (TextView) view.findViewById(R.id.tv_action_jiesuan);

        // 设置ListView
        setupListView();
        // 设置SwipeRefreshLayout
        setupSwipe();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && isFirstVisible && isLogin) {
            // 打开加载提示，并执行加载购物车数据请求
            showProgressDialog("正在加载");
            loadShopCartData(uid);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isLogin = App.getPref("isLogin", false);
        if (isLogin) {
            // 获取当前用户的uid
            UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
            uid = String.valueOf(userModel.getUserInfo().getUid());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注销事件
        EventBus.getDefault().unregister(this);
    }

    /**
     * 接收事件的方法
     *
     * @param amount 用户对象
     */
    @Subscriber(tag = "CartAdapter_tag")
    public void refreshView(double amount) {
        shopCartModel.setProductAmount(amount);
        tvSum.setText(amount+"");
    }

    /**
     * 设置ListView
     */
    private void setupListView() {
        // 设置适配器
        adapter = new ShopCarAdapter(getActivity(), shopCartModel);
        listView.setAdapter(adapter);
    }

    /**
     * 设置Swipe
     */
    private void setupSwipe() {
        // 下拉监听
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 清空数据,并刷新适配器
                shopCartModel.getCartGoodsBeanList().clear();
                adapter.notifyDataSetChanged();
                // 打开加载提示，并执行加载购物车数据请求
                showProgressDialog("正在加载");
                loadShopCartData(uid);
            }
        });
    }

    /**
     * 获取购物车数据
     *
     * @param uid 用户id
     */
    private void loadShopCartData(String uid) {
        BrnmallAPI.getMyCart(uid, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                // 关闭加载提示
                dismissProgressDialog();
                swipe.setRefreshing(false);
            }

            @Override
            public void onResponse(String response) {
                // 关闭加载提示
                dismissProgressDialog();
                swipe.setRefreshing(false);
                TLog.d("购物车=  " + response);
                if (TextUtils.isEmpty(response)){
                    TLog.e("返回空");
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("result").equals("false")){
                        TLog.e("返回false");
                        return;
                    }
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    shopCartModel.setTotalCount(dataObject.getInt("TotalCount"));
                    shopCartModel.setProductAmount(dataObject.getDouble("ProductAmount"));
                    shopCartModel.setFullCut(dataObject.getInt("FullCut"));
                    shopCartModel.setOrderAmount(dataObject.getDouble("OrderAmount"));
                    shopCartModel.setCartGoodsBeanList(ShopCartModel.CartGoodsBean.jsonStrToList(dataObject.getString("StoreCartList")));

                    tvSum.setText(shopCartModel.getProductAmount() + "");
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
