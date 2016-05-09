package com.luoyp.brnmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.adapter.EditOrderGoodsListAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.ShopCartModel;
import com.luoyp.brnmall.model.UserModel;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

public class EditOrderActy extends BaseActivity {

    ShopCartModel shopCartModel;
    EditOrderGoodsListAdapter adapter;
    private android.widget.Button getAddress;
    private android.widget.ListView shopcarlistview;
    private TextView tvsum;
    private android.widget.Button btntijiandingdan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_order);
        this.btntijiandingdan = (Button) findViewById(R.id.btn_tijiandingdan);
        this.tvsum = (TextView) findViewById(R.id.tv_sum);
        this.shopcarlistview = (ListView) findViewById(R.id.shopcarlistview);
        this.getAddress = (Button) findViewById(R.id.getAddress);
        shopCartModel = App.shopCar;

        KLog.d(shopCartModel);
        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("填写订单");
        }
        btntijiandingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postOrder();
            }
        });
        adapter = new EditOrderGoodsListAdapter(this, shopCartModel);
        shopcarlistview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        totalPrice();
    }

    public void totalPrice() {
        double sum = 0.0;
        int goodsCount = shopCartModel.getCartGoodsBeanList().size();
        if (goodsCount == 0) {
            tvsum.setText("");
        }
        for (int i = 0; i < goodsCount; i++) {
            sum += shopCartModel.getCartGoodsBeanList().get(i).getShopPrice() * shopCartModel.getCartGoodsBeanList().get(i).getBuyCount();
        }
        tvsum.setText(String.format("%.2f", sum));
    }

    public void postOrder() {
        if (!checkLogin()) {
            return;
        }
        // 获取当前用户的uid
        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        String uid = String.valueOf(userModel.getUserInfo().getUid());
        int count = shopCartModel.getCartGoodsBeanList().size();
        String list = "";
        for (int i = 0; i < count; i++) {
            if (i == count - 1) {
                list += "0_" + shopCartModel.getCartGoodsBeanList().get(i).getPid();
            } else {
                list += "0_" + shopCartModel.getCartGoodsBeanList().get(i).getPid() + ",";
            }
        }
        KLog.d("提交订单商品list:" + list);
        showProgressDialog("正在提交订单");
        BrnmallAPI.createOrder(uid, "23", list, "alipay", "", new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                showToast("网络异常,请稍后再试吧");
            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                if (response == null || TextUtils.isEmpty(response)) {
                    showToast("服务异常,请稍后再试吧");
                    return;
                }
                try {
                    JSONObject json = new JSONObject(response);
                    KLog.json("提交订单返回json", json.toString());
                    if ("false".equals(json.getString("result"))) {
                        showToast(json.getJSONArray("data").getJSONObject(0).getString("msg"));
                        return;
                    }

                    EventBus.getDefault().post("", "CartAdapter_tag");
                    showToast("提交订单成功");
                    Intent intent = new Intent();
                    intent.putExtra("oid", json.getJSONObject("data").getString("Oid"));
                    intent.putExtra("osn", json.getJSONObject("data").getString("OSN"));
                    intent.putExtra("price", json.getJSONObject("data").getString("OrderAmount"));
                    intent.setClass(EditOrderActy.this, PayActivity.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

//            vc.order=MyOrder.init(id:json["data"]["Oid"].intValue,title:json["data"]["OSN"].stringValue,content:json["data"]["StoreName"].stringValue,url:"",createdAt:"",price:json["data"]["OrderAmount"].doubleValue,paid:true,productID:json["data"]["Oid"].intValue)


        });
    }
}
