package com.luoyp.brnmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.SysUtils;
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
    private String aid = "";
    private android.widget.RelativeLayout address;
    private TextView ordername;
    private TextView orderphone;
    private TextView orderaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_order);
        this.orderaddress = (TextView) findViewById(R.id.orderaddress);
        this.orderphone = (TextView) findViewById(R.id.orderphone);
        this.ordername = (TextView) findViewById(R.id.ordername);
        this.address = (RelativeLayout) findViewById(R.id.address);
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
        if (App.getPref("isLogin", false)) {
            tvsum.setText(SysUtils.formatDouble((Double.valueOf(App.getPref("zhekou", "10")) * sum * 10 / 100)) + " (" + App.getPref("zhekoutitle", "") + ")");
        } else {
            tvsum.setText(SysUtils.formatDouble(sum) + " (" + App.getPref("zhekoutitle", "") + ")");
        }
    }

    public void postOrder() {
        if (!checkLogin()) {
            return;
        }
        if (TextUtils.isEmpty(aid)) {
            showToast("请选择收货地址");
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
        BrnmallAPI.createOrder(uid, aid, list, "alipay", "", new ApiCallback<String>() {
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
                    if (App.getPref("isLogin", false)) {
                        tvsum.setText(SysUtils.formatDouble((Double.valueOf(App.getPref("zhekou", "10")) * Double.valueOf(json.getJSONObject("data").getString("OrderAmount")) * 10 / 100)) + " (" + App.getPref("zhekoutitle", "") + ")");
                    } else {
                        intent.putExtra("price", json.getJSONObject("data").getString("OrderAmount"));
                    }

                    intent.setClass(EditOrderActy.this, PayActivity.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getAddress(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MyAddressActivity.class);
        intent.putExtra("isSelect", true);

        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK) {
            aid = data.getExtras().getString("aid");//得到新Activity 关闭后返回的数据
            orderaddress.setText("收货地址: " + data.getExtras().getString("address"));
            ordername.setText("收 货 人: " + data.getExtras().getString("name"));
            orderphone.setText("联系电话: " + data.getExtras().getString("phone"));
        }

    }
}
