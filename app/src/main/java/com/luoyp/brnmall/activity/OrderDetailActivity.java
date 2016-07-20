package com.luoyp.brnmall.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.adapter.OrderGoodsAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.OrderDetailModel;
import com.luoyp.brnmall.model.UserModel;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderDetailActivity extends BaseActivity {

    private TextView storeName, stateName, shipfee, amount, userName, userPhone, address;
    private ListView listViewGoods;
    private OrderGoodsAdapter adapterGoods;
    private OrderDetailModel orderModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order_detail);

        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("订单详情");
        }

        orderModel = new OrderDetailModel();
        orderModel.setOrdrGoodsList(new ArrayList<OrderDetailModel.OrderGoodsBean>());

        storeName = (TextView) findViewById(R.id.tv_order_store_name);
        stateName = (TextView) findViewById(R.id.tv_order_state_name);
        shipfee = (TextView) findViewById(R.id.tv_order_shipfee);
        amount = (TextView) findViewById(R.id.tv_order_amount);
        userName = (TextView) findViewById(R.id.tv_order_user_name);
        userPhone = (TextView) findViewById(R.id.tv_order_user_phone);
        address = (TextView) findViewById(R.id.tv_order_address);
        listViewGoods = (ListView) findViewById(R.id.lv_order_goods);

        adapterGoods = new OrderGoodsAdapter(OrderDetailActivity.this, orderModel.getOrderGoodsList());
        listViewGoods.setAdapter(adapterGoods);

        String oid = getIntent().getStringExtra("oid");
        // 获取当前用户的uid
        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        String uid = String.valueOf(userModel.getUserInfo().getUid());
        getOrderDetail(uid, oid);


    }

    /**
     * 获取订单详情
     *
     * @param uid 用户id
     * @param oid 订单id
     */
    private void getOrderDetail(String uid, String oid) {
        BrnmallAPI.getOrderDetail(uid, oid, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                KLog.json(response);

                if (TextUtils.isEmpty(response)) {
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ("false".equals(jsonObject.getString("result"))) {
                        return;
                    }
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    orderModel.setOrderInfo(new Gson().fromJson(dataObject.getString("OrderInfo"), OrderDetailModel.OrderBean.class));
                    orderModel.setRegionInfo(new Gson().fromJson(dataObject.getString("RegionInfo"), OrderDetailModel.RegionBean.class));
                    //orderModel.setOrdrGoodsList(OrderDetailModel.jsonToOrderGoodsList(dataObject.getString("OrderProductList")));
                    orderModel.getOrderGoodsList().addAll(orderModel.jsonToOrderGoodsList(dataObject.getString("OrderProductList")));
                    orderModel.setOrderActionList(OrderDetailModel.jsonToOrderActionList(dataObject.getString("OrderActionList")));

                    adapterGoods.notifyDataSetChanged();

                    storeName.setText(orderModel.getOrderInfo().getStoreName());
                    String state = orderModel.getOrderInfo().getOrderState();
                    if ("10".equals(state)) {
                        stateName.setText("已提交");
                    }
                    if ("30".equals(state)) {
                        stateName.setText("等待付款");
                    }
                    if ("50".equals(state)) {
                        stateName.setText("确认中");
                    }
                    if ("70".equals(state)) {
                        stateName.setText("已确认");
                    }
                    if ("90".equals(state)) {
                        stateName.setText("已备货");
                    }
                    if ("110".equals(state)) {
                        stateName.setText("已发货");
                    }
                    if ("140".equals(state)) {
                        stateName.setText("已收货");
                    }
                    if ("160".equals(state)) {
                        stateName.setText("已完成");
                    }
                    if ("180".equals(state)) {
                        stateName.setText("已退货");
                    }
                    if ("200".equals(state)) {
                        stateName.setText("已取消");
                    }
                    shipfee.setText("￥" + orderModel.getOrderInfo().getShipFee());
                    amount.setText("￥" + orderModel.getOrderInfo().getSurplusMoney() + " (" + App.getPref("zhekoutitle", "") + ")");
                    userName.setText(orderModel.getOrderInfo().getConsignee());
                    userPhone.setText(orderModel.getOrderInfo().getMobile());
                    address.setText(orderModel.getOrderInfo().getAddress());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
