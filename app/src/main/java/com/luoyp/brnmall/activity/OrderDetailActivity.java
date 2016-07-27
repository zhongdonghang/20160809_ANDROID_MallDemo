package com.luoyp.brnmall.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListAdapter;
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

    String oid = "";
    String uid = "";
    private TextView storeName, stateName, shipfee, amount, userName, userPhone, address;
    private ListView listViewGoods;
    private OrderGoodsAdapter adapterGoods;
    private OrderDetailModel orderModel;
    private TextView tvdingdanhao;
    private TextView tvbeisongdanhao;
    private TextView tvxiadanshijian;
    private TextView tvbeisongshijian;
    private TextView tvbeisongfangshi;
    private TextView tvzhifufangshi;
    private TextView tvorderusername;
    private TextView tvorderuserphone;
    private TextView tvorderaddress;
    private TextView tvorderstorename;
    private TextView tvorderstatename;
    private ListView lvordergoods;
    private TextView tvordershipfee;
    private TextView tvorderamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order_detail);
        this.tvorderamount = (TextView) findViewById(R.id.tv_order_amount);
        this.tvordershipfee = (TextView) findViewById(R.id.tv_order_shipfee);
        this.lvordergoods = (ListView) findViewById(R.id.lv_order_goods);
        this.tvorderstatename = (TextView) findViewById(R.id.tv_order_state_name);
        this.tvorderstorename = (TextView) findViewById(R.id.tv_order_store_name);
        this.tvorderaddress = (TextView) findViewById(R.id.tv_order_address);
        this.tvorderuserphone = (TextView) findViewById(R.id.tv_order_user_phone);
        this.tvorderusername = (TextView) findViewById(R.id.tv_order_user_name);
        this.tvzhifufangshi = (TextView) findViewById(R.id.tvzhifufangshi);
        this.tvbeisongfangshi = (TextView) findViewById(R.id.tvbeisongfangshi);
        this.tvbeisongshijian = (TextView) findViewById(R.id.tvbeisongshijian);
        this.tvxiadanshijian = (TextView) findViewById(R.id.tvxiadanshijian);
        this.tvbeisongdanhao = (TextView) findViewById(R.id.tvbeisongdanhao);
        this.tvdingdanhao = (TextView) findViewById(R.id.tvdingdanhao);

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


        oid = getIntent().getStringExtra("oid");
        // 获取当前用户的uid
        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        uid = String.valueOf(userModel.getUserInfo().getUid());
        showProgressDialog("正在加载订单信息");
        getOrderDetail(uid, oid);


    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + 150 + (listView.getDividerHeight() * (listAdapter.getCount()));
        listView.setLayoutParams(params);
    }

    /**
     * 获取订单详情
     *
     * @param uid 用户id
     * @param oid 订单id
     */
    private void getOrderDetail(String uid, final String oid) {
        BrnmallAPI.getOrderDetail(uid, oid, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                showToast("网络异常,请稍后再试吧");
            }

            @Override
            public void onResponse(String response) {
                KLog.json(response);
                dismissProgressDialog();

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
                    setListViewHeightBasedOnChildren(listViewGoods);
                    adapterGoods.notifyDataSetChanged();


                    storeName.setText(orderModel.getOrderInfo().getStoreName());
                    tvdingdanhao.setText(orderModel.getOrderInfo().getOSN());
                    tvbeisongdanhao.setText(orderModel.getOrderInfo().getShipSN());
                    tvxiadanshijian.setText(orderModel.getOrderInfo().getAddTime());
                    if (orderModel.getOrderInfo().getShipTime().contains("1900")) {
                        tvbeisongshijian.setText("");
                    } else {
                        tvbeisongshijian.setText(orderModel.getOrderInfo().getShipTime());
                    }
                    tvbeisongfangshi.setText(orderModel.getOrderInfo().getShipCoName());
                    tvzhifufangshi.setText(orderModel.getOrderInfo().getPayFriendName());

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
                    userName.setText("收 货 人: " + orderModel.getOrderInfo().getConsignee());
                    userPhone.setText(orderModel.getOrderInfo().getMobile());
                    address.setText("收货地址: " + orderModel.getRegionInfo().getProvinceName() + orderModel.getRegionInfo().getCityName() + orderModel.getRegionInfo().getName() + orderModel.getOrderInfo().getAddress());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
