package com.luoyp.brnmall.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.adapter.RelationAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.RelationModel;
import com.luoyp.brnmall.model.UserModel;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyRelationActivity extends BaseActivity {

    RelationAdapter adapter;
    List<RelationModel> list;

    private com.handmark.pulltorefresh.library.PullToRefreshListView myrelationlistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_relation);
        this.myrelationlistview = (PullToRefreshListView) findViewById(R.id.my_relation_list_view);


        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("介绍人关系");
        }
        list = new ArrayList<>();
        adapter = new RelationAdapter(this, list);
        myrelationlistview.setAdapter(adapter);
        myrelationlistview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        myrelationlistview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                list.clear();
                getMyRelation();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        showProgressDialog("正在加载数据");
        getMyRelation();
    }

    public void getMyRelation() {
        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        String uid = String.valueOf(userModel.getUserInfo().getUid());
        BrnmallAPI.getIntroducers(uid, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                myrelationlistview.onRefreshComplete();
                showToast("网络异常,请稍后再试");
            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                myrelationlistview.onRefreshComplete();
                if (response != null && !response.isEmpty()) {
                    try {
                        JSONObject j = new JSONObject(response);
                        JSONObject json = j.getJSONObject("data");
                        KLog.d("介绍人json :" + j.toString());
                        if ("false".equals(j.getString("result"))) {
                            showToast("暂时无数据返回,请稍后再试");
                            return;
                        }


                        RelationModel model = new RelationModel();
                        model.setAddTime("-1");
                        list.add(model);
                        if (!json.isNull("introducer")) {
                            JSONObject jsonObject = json.getJSONObject("introducer");
                                RelationModel m = new RelationModel();
                            m.setAddTime(jsonObject.getString("AddTime"));
                            m.setNickName(jsonObject.getString("NickName"));
                            m.setRealName(jsonObject.getString("UserName"));
                                list.add(m);

                        }

                        model = new RelationModel();
                        model.setAddTime("-2");
                        list.add(model);
                        JSONArray myIntroducerJson = json.getJSONArray("MyIntroducers");
                        for (int i = 0; i < myIntroducerJson.length(); i++) {
                            RelationModel m = new RelationModel();
                            m.setAddTime(myIntroducerJson.getJSONObject(i).getString("AddTime"));
                            m.setNickName(myIntroducerJson.getJSONObject(i).getString("NickName"));
                            m.setRealName(myIntroducerJson.getJSONObject(i).getString("UserName"));
                            list.add(m);
                        }

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
