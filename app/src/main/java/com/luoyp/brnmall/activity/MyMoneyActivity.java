package com.luoyp.brnmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.adapter.PaylogAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.PayCreditLogModel;
import com.luoyp.brnmall.model.UserModel;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyMoneyActivity extends BaseActivity {

    PaylogAdapter adapter;
    List<PayCreditLogModel> list;
    int pageIndex = 1;
    double ketixian = 0.0;
    double dongjie = 0.0;
    private de.hdodenhof.circleimageview.CircleImageView ivavatar;
    private TextView tvketixian;
    private TextView tvdongjie;
    private android.widget.LinearLayout llllll;
    private com.handmark.pulltorefresh.library.PullToRefreshListView mymoneylistview;
    private android.widget.Button btntixianlog;
    private android.widget.Button btntixian;
    private android.widget.RelativeLayout activitymymoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_money);
        this.activitymymoney = (RelativeLayout) findViewById(R.id.activity_my_money);
        this.btntixian = (Button) findViewById(R.id.btn_tixian);
        this.btntixianlog = (Button) findViewById(R.id.btn_tixianlog);
        this.mymoneylistview = (PullToRefreshListView) findViewById(R.id.my_money_list_view);
        this.llllll = (LinearLayout) findViewById(R.id.llllll);
        this.tvdongjie = (TextView) findViewById(R.id.tv_dongjie);
        this.tvketixian = (TextView) findViewById(R.id.tv_ketixian);
        this.ivavatar = (CircleImageView) findViewById(R.id.iv_avatar);

        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("我的资产");
        }
        App.getPicasso().load(BrnmallAPI.userImgUrl + App.getPref("avatar", "")).error(R.mipmap.logo).placeholder(R.mipmap.logo).into(ivavatar);

        list = new ArrayList<>();
        adapter = new PaylogAdapter(this, list);
        mymoneylistview.setAdapter(adapter);
        mymoneylistview.setMode(PullToRefreshBase.Mode.BOTH);
        mymoneylistview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                list.clear();
                pageIndex = 1;
                getPaylog(pageIndex + "");

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getPaylog(pageIndex + "");
            }
        });
        showProgressDialog("正在加载数据");
        getPaylog(pageIndex + "");

        btntixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ketixian > 0.2) {
                    Intent intent = new Intent(MyMoneyActivity.this, ApplyActivity.class);
                    intent.putExtra("ketixian", ketixian);
                    startActivity(intent);

                    return;
                }
                showToast("可提现资产必须达到 0.2 元才可提现");
            }
        });
        btntixianlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MyMoneyActivity.this, TixianLogActivity.class));


            }
        });
    }

    public void getPaylog(String index) {
        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        String uid = String.valueOf(userModel.getUserInfo().getUid());
        BrnmallAPI.payCreditList(uid, index, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                mymoneylistview.onRefreshComplete();
                dismissProgressDialog();
                showToast("网络异常,请稍后再试");
            }

            @Override
            public void onResponse(String response) {
                mymoneylistview.onRefreshComplete();
                dismissProgressDialog();

                if (response != null && !response.isEmpty()) {
                    try {
                        JSONObject j = new JSONObject(response);
                        JSONObject json = j.getJSONObject("data");
                        if ("false".equals(j.getString("result"))) {
                            showToast("暂时无数据返回,请稍后再试");
                            return;
                        }

                        ketixian = json.getDouble("UserAmount");
                        dongjie = json.getDouble("UserFrozenAmount");
                        JSONArray jsonArray = json.getJSONArray("PayCreditLogList");
                        if (jsonArray.length() == 0) {
                            showToast("没有数据了");
                            adapter.notifyDataSetChanged();
                            return;
                        }

                        for (int i = 0; i < jsonArray.length(); i++) {
                            PayCreditLogModel m = new PayCreditLogModel();
                            m.setActionDes(jsonArray.getJSONObject(i).getString("ActionDes"));
                            m.setActionTime(jsonArray.getJSONObject(i).getString("ActionTime"));
                            m.setFrozenAmount(jsonArray.getJSONObject(i).getString("FrozenAmount"));
                            m.setUserAmount(jsonArray.getJSONObject(i).getString("UserAmount"));
                            list.add(m);
                        }

                        tvketixian.setText("可提现资产：" + ketixian + " 元");
                        tvdongjie.setText(" 冻结资产：" + dongjie + " 元");

                        pageIndex++;
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    showToast("暂时无数据返回,请稍后再试");
                }
            }
        });
    }
}