package com.luoyp.brnmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.adapter.MyAddressAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.MyAddressModel;
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

public class MyAddressActivity extends BaseActivity {

    private com.handmark.pulltorefresh.library.PullToRefreshListView myaddresslistview;
    private List<MyAddressModel> addressList;
    private MyAddressAdapter adapter;
    private boolean isSelect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_address);
        EventBus.getDefault().register(this);
        this.myaddresslistview = (PullToRefreshListView) findViewById(R.id.my_address_list_view);
        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("我的收货地址");
        }

        isSelect = getIntent().getBooleanExtra("isSelect", false);
        ImageButton btn = (ImageButton) findViewById(R.id.topbar_right);
        btn.setVisibility(View.VISIBLE);
        btn.setBackgroundResource(R.drawable.ic_add_box_white_18dp);
        addressList = new ArrayList<>();
        adapter = new MyAddressAdapter(this, addressList);
        myaddresslistview.setAdapter(adapter);
        myaddresslistview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        myaddresslistview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                addressList.clear();
                getMyAddress();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        myaddresslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KLog.d("地址id" + addressList.get(position - 1).getAid());
                if (isSelect) {
                    //数据是使用Intent返回
                    Intent intent = new Intent();
                    //把返回数据存入Intent
                    intent.putExtra("aid", addressList.get(position - 1).getAid());
                    intent.putExtra("name", addressList.get(position - 1).getName());
                    intent.putExtra("phone", addressList.get(position - 1).getMobile());
                    intent.putExtra("address", addressList.get(position - 1).getProvinceName() + addressList.get(position - 1).getCityName() + addressList.get(position - 1).getCountyName() + addressList.get(position - 1).getAddress());
                    //设置返回数据
                    MyAddressActivity.this.setResult(RESULT_OK, intent);
                    //关闭Activity
                    MyAddressActivity.this.finish();
                }
            }
        });
        showProgressDialog("正在加载数据");
        getMyAddress();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscriber(tag = "editaddress")
    public void editAdderss(int pos) {
        //showToast("地址id = " + addressList.get(pos).getAid());
        Intent intent = new Intent(this, EditAddressActivity.class);
        intent.putExtra("address", addressList.get(pos));
        startActivity(intent);
    }

    @Subscriber(tag = "refreshAdderss")
    public void refreshAdderss(String s) {
        addressList.clear();
        getMyAddress();
    }

    @Override
    public void add(View view) {
        startActivity(new Intent(this, AddMyAddressActivity.class));
    }

    public void getMyAddress() {
        // 获取当前用户的uid
        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        String uid = String.valueOf(userModel.getUserInfo().getUid());
        BrnmallAPI.getMyAddress(uid, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                myaddresslistview.onRefreshComplete();
                showToast("网络异常,请稍后再试吧");
            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                //  KLog.d("地址json " + response);
                myaddresslistview.onRefreshComplete();

                if (response == null || TextUtils.isEmpty(response)) {
                    showToast("没有数据返回,请稍后再试吧");
                    return;
                }
                try {
                    JSONObject json = new JSONObject(response);
                    KLog.d("返回地址json " + json.toString());

                    if ("false".equals(json.getString("result"))) {
                        showToast("没有数据返回,请稍后再试吧");
                        return;
                    }

                    JSONArray addresslist = json.getJSONObject("data").getJSONArray("ShipAddressList");
                    if (addresslist.length() == 0) {
                        showToast("还没有地址哦,新增一个吧");
                        return;
                    }
                    if (addresslist.length() >= 1) {
                        for (int i = 0; i < addresslist.length(); i++) {
                            MyAddressModel model = new MyAddressModel();
                            JSONObject object = addresslist.getJSONObject(i);
                            model.setAddress(object.getString("Address"));
                            model.setUid(object.getString("Uid"));
                            model.setAid(object.getString("SAId"));
                            model.setName(object.getString("Consignee"));
                            model.setRegionId(object.getString("RegionId"));
                            model.setMobile(object.getString("Mobile"));
                            model.setPhone(object.getString("Phone"));
                            model.setZipcode(object.getString("ZipCode"));
                            model.setEmail(object.getString("Email"));
                            model.setIsDefault(object.getString("IsDefault"));
                            model.setProvinceName(object.getString("ProvinceName"));

                            model.setProvinceId(object.getString("ProvinceId"));
                            model.setCityId(object.getString("CityId"));
                            model.setCityName(object.getString("CityName"));
                            model.setCountyId(object.getString("CountyId"));
                            model.setCountyName(object.getString("CountyName"));
                            addressList.add(model);
                        }
                        //    myaddresslistview.invalidate();
                        adapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
