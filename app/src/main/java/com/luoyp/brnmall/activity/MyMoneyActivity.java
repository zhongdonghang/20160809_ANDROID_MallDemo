package com.luoyp.brnmall.activity;

import android.os.Bundle;
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
        mymoneylistview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mymoneylistview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                list.clear();
                pageIndex = 1;
                getPaylog(pageIndex + "");

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        showProgressDialog("正在加载数据");
        getPaylog(pageIndex + "");

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
