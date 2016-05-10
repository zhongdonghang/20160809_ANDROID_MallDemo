package com.luoyp.brnmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.adapter.MyOrderAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.Goods;
import com.luoyp.brnmall.model.MyOrderModel;
import com.luoyp.brnmall.model.UserModel;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends BaseActivity {

    MyOrderAdapter adapter;
    List<MyOrderModel> list;
    private com.handmark.pulltorefresh.library.PullToRefreshListView myorderlistview;
    private int pageIndex = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_my_order);
        this.myorderlistview = (PullToRefreshListView) findViewById(R.id.my_order_list_view);
        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("我的订单");
        }
        list = new ArrayList<MyOrderModel>();
        adapter = new MyOrderAdapter(this, list);
        myorderlistview.setAdapter(adapter);
        myorderlistview.setMode(PullToRefreshBase.Mode.BOTH);
        myorderlistview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                list.clear();
                pageIndex = 1;
                getMyOder();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getMyOder();
            }
        });
        myorderlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KLog.d("订单id " + list.get(position - 1).getOid());
            }
        });
        showProgressDialog("正在加载数据");
        getMyOder();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscriber(tag = "refreshorder")
    public void payRefresh(String s) {
        list.clear();
        pageIndex = 1;
        getMyOder();
    }

    @Subscriber(tag = "paynow")
    public void paynow(int pos) {
        pay(pos);
    }

    public void pay(int pos) {
        Intent intent = new Intent();
        intent.putExtra("oid", list.get(pos).getOid());
        intent.putExtra("osn", list.get(pos).getOsn());
        intent.putExtra("price", list.get(pos).getOrderamount());
        intent.setClass(MyOrderActivity.this, PayActivity.class);
        startActivity(intent);
    }

    @Subscriber(tag = "cancelorder")
    public void cancelorder(String s) {

        showToast("取消订单 oid=" + s);
    }

    public void getMyOder() {
        // 获取当前用户的uid
        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        String uid = String.valueOf(userModel.getUserInfo().getUid());

        BrnmallAPI.getMyOrderList(uid, pageIndex + "", "", new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                myorderlistview.onRefreshComplete();
                showToast("网络异常,请稍后再试吧");
            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                myorderlistview.onRefreshComplete();

                if (response == null || TextUtils.isEmpty(response)) {
                    showToast("没有数据返回,请稍后再试吧");
                    return;
                }
                try {
                    JSONObject json = new JSONObject(response);
                    KLog.d("我的订单" + json.toString());
                    if ("false".equals(json.getString("result"))) {
                        showToast("没有数据返回,请稍后再试吧");
                        return;
                    }
                    JSONArray orderlist = json.getJSONObject("data").getJSONArray("OrderList");
                    if (orderlist.length() == 0) {
                        showToast("您还没有订单,去逛一逛吧");
                        return;
                    }
                    if (orderlist.length() >= 1) {

                        for (int i = 0; i < orderlist.length() - 1; i++) {
                            MyOrderModel myOrderModel = new MyOrderModel();
                            List<Goods> goodslist = new ArrayList<Goods>();
                            if (orderlist.getJSONObject(i).getJSONArray("ProList").length() >= 1) {
                                for (int j = 0; j < orderlist.getJSONObject(i).getJSONArray("ProList").length(); j++) {
                                    Goods goods = new Goods();
                                    goods.setImg(orderlist.getJSONObject(i).getJSONArray("ProList").getJSONObject(j).getString("ShowImg"));
                                    goods.setPname(orderlist.getJSONObject(i).getJSONArray("ProList").getJSONObject(j).getString("PName"));
                                    goods.setPid(orderlist.getJSONObject(i).getJSONArray("ProList").getJSONObject(j).getString("PId"));
                                    goodslist.add(goods);
                                }
                            }

                            myOrderModel.setGoodsList(goodslist);
                            myOrderModel.setAddtime(orderlist.getJSONObject(i).getString("AddTime"));
                            myOrderModel.setUid(orderlist.getJSONObject(i).getString("UId"));
                            myOrderModel.setOid(orderlist.getJSONObject(i).getString("OId"));
                            myOrderModel.setOrderstate(orderlist.getJSONObject(i).getString("OrderState"));
                            myOrderModel.setOrderamount(orderlist.getJSONObject(i).getString("OrderAmount"));
                            myOrderModel.setStoreid(orderlist.getJSONObject(i).getString("StoreId"));
                            myOrderModel.setStorename(orderlist.getJSONObject(i).getString("StoreName"));
                            myOrderModel.setPayfriendname(orderlist.getJSONObject(i).getString("PayFriendName"));
                            myOrderModel.setOsn(orderlist.getJSONObject(i).getString("OSN"));
                            list.add(myOrderModel);
                        }

                        pageIndex++;
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    showToast("数据解析异常,请稍后再试吧");
                    e.printStackTrace();
                }
            }
        });
    }
}
