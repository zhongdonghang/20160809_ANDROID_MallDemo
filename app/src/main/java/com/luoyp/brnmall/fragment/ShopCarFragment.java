package com.luoyp.brnmall.fragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.activity.EditOrderActy;
import com.luoyp.brnmall.adapter.ShopCarAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.ShopCartModel;
import com.luoyp.brnmall.model.UserModel;
import com.socks.library.KLog;
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
    private TextView tvSum;
    private ShopCartModel shopCartModel;
    private Button jiesuanBtn;
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
        jiesuanBtn = (Button) view.findViewById(R.id.btn_jiesuan);
        listView = (ListView) view.findViewById(R.id.listView);
        tvSum = (TextView) view.findViewById(R.id.tv_sum);

        jiesuanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jiesuan();
            }
        });
        // 设置ListView
        setupListView();
        // 设置SwipeRefreshLayout
        setupSwipe();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                KLog.d("删除购物车position " + pos);
                KLog.d("删除购物车id " + shopCartModel.getCartGoodsBeanList().get(pos).getPid());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确定删除商品 [" + shopCartModel.getCartGoodsBeanList().get(pos).getName() + "] 吗?");

                builder.setTitle("删除提示");
                builder.setCancelable(false);
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFromShopcar(shopCartModel.getCartGoodsBeanList().get(pos).getPid() + "");
                    }
                });
                builder.create().show();
            }
        });
        return view;
    }

    public void jiesuan() {
        KLog.d("点击结算");
        if (!checkLogin()) {
            return;
        }
        App.shopCar = shopCartModel;

        Intent intent = new Intent();
        //   intent.putExtra("shopcar", shopCartModel);
        intent.setClass(getActivity(), EditOrderActy.class);
        startActivity(intent);
    }

    public void deleteFromShopcar(String id) {
        // 获取当前用户的uid
        final UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        uid = String.valueOf(userModel.getUserInfo().getUid());

        BrnmallAPI.deleteCartProduct(id, uid, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                KLog.d("删除购物车返回json " + response);
                if (response != null && !TextUtils.isEmpty(response)) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        if ("true".equals(jsonObject.getString("result"))) {
                            loadShopCartData(uid);

                        }
                        showToast(jsonObject.getJSONArray("data").getJSONObject(0).getString("msg"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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
     */
    @Subscriber(tag = "CartAdapter_tag")
    public void refreshView(String s) {
        //  tvSum.setText(amount + "");
        loadShopCartData(uid);
    }

    public void totalPrice() {
        double sum = 0.0;
        int goodsCount = shopCartModel.getCartGoodsBeanList().size();
        if (goodsCount == 0) {
            tvSum.setText("");
        }
        for (int i = 0; i < goodsCount; i++) {
            sum += shopCartModel.getCartGoodsBeanList().get(i).getShopPrice() * shopCartModel.getCartGoodsBeanList().get(i).getBuyCount();
        }
        tvSum.setText(String.format("%.2f", sum));
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
                swipe.setRefreshing(false);
                if (!checkLogin()) {
                    return;
                }
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
                KLog.json("购物车=  " + response);
                if (TextUtils.isEmpty(response)) {

                    KLog.d("返回空");
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    if (jsonObject.getString("result").equals("false") || dataObject.getJSONArray("StoreCartList").length() == 0) {
                        shopCartModel.getCartGoodsBeanList().clear();
                        adapter.notifyDataSetChanged();
                        tvSum.setText("");
                        // showToast("购物车空空,去逛一逛吧");
                        jiesuanBtn.setEnabled(false);
                        return;
                    }

                    shopCartModel.setTotalCount(dataObject.getInt("TotalCount"));
                    shopCartModel.setProductAmount(dataObject.getDouble("ProductAmount"));
                    shopCartModel.setFullCut(dataObject.getInt("FullCut"));
                    shopCartModel.setOrderAmount(dataObject.getDouble("OrderAmount"));
                    shopCartModel.setCartGoodsBeanList(ShopCartModel.CartGoodsBean.jsonStrToList(dataObject.getString("StoreCartList")));
                    totalPrice();
                    jiesuanBtn.setEnabled(true);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
