package com.luoyp.brnmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.adapter.TixianlogAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.TixianLogModel;
import com.luoyp.brnmall.model.UserModel;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TixianLogActivity extends BaseActivity {

    TixianlogAdapter adapter;
    List<TixianLogModel> list;
    int pageIndex = 1;
    private com.handmark.pulltorefresh.library.PullToRefreshListView mytixianloglistview;
    private android.widget.RelativeLayout activitytixianlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tixian_log);
        this.activitytixianlog = (RelativeLayout) findViewById(R.id.activity_tixian_log);
        this.mytixianloglistview = (PullToRefreshListView) findViewById(R.id.my_tixianlog_list_view);

        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("提现记录");
        }

        list = new ArrayList<>();
        adapter = new TixianlogAdapter(this, list);
        mytixianloglistview.setAdapter(adapter);
        mytixianloglistview.setMode(PullToRefreshBase.Mode.BOTH);
        mytixianloglistview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                list.clear();
                pageIndex = 1;
                getTixianLog(pageIndex + "");

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getTixianLog(pageIndex + "");
            }
        });
        mytixianloglistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                App.tixianLogModel = list.get(position - 1);
                startActivity(new Intent(TixianLogActivity.this, TixianDetailActivity.class));
            }
        });
        showProgressDialog("正在加载数据");
        getTixianLog(pageIndex + "");

    }

    public void getTixianLog(String index) {
        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        String uid = String.valueOf(userModel.getUserInfo().getUid());

        BrnmallAPI.withdrawlList(uid, index, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                mytixianloglistview.onRefreshComplete();
                dismissProgressDialog();
                showToast("网络异常,请稍后再试");
            }

            @Override
            public void onResponse(String response) {
                mytixianloglistview.onRefreshComplete();
                dismissProgressDialog();
                if (response != null && !response.isEmpty()) {
                    try {
                        JSONObject j = new JSONObject(response);
                        JSONObject json = j.getJSONObject("data");
                        if ("false".equals(j.getString("result"))) {
                            showToast("暂时无数据返回,请稍后再试");
                            return;
                        }

                        JSONArray jsonArray = json.getJSONArray("WithdrawalLogList");
                        if (jsonArray.length() == 0) {
                            showToast("没有数据了");
                            adapter.notifyDataSetChanged();
                            return;
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            TixianLogModel m = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), TixianLogModel.class);

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
