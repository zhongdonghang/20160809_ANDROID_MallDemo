package com.luoyp.brnmall.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.adapter.GoodsReviewAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.GoodsReview;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GoodsReviewActivity extends BaseActivity {

    private PullToRefreshListView listView;

    private List<GoodsReview> reviews;
    private GoodsReviewAdapter adapter;
    private String pid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_review);
        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("商品评价");
        }

        listView = (PullToRefreshListView) this.findViewById(R.id.goods_review_listview);

        pid = getIntent().getStringExtra("pid");

        reviews = new ArrayList<GoodsReview>();
        adapter = new GoodsReviewAdapter(this,reviews);
        listView.setAdapter(adapter);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                reviews.clear();
                loadGoodsReview(pid);
            }
        });
        showProgressDialog("正在加载...");
        loadGoodsReview(pid);
    }

    /**
     * 获取商品评价
     * @param id
     */
    private void loadGoodsReview(String id) {
        BrnmallAPI.getGoodsReview(id, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                listView.onRefreshComplete();
                showToast("请稍后再试");
            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                listView.onRefreshComplete();
                KLog.json("商品评价  "+response);
                if (TextUtils.isEmpty(response)) {
                    showToast("请稍后再试");
                    return;
                }
                try {
                    JSONObject jsonObj = new JSONObject(response);

                    if (jsonObj.getBoolean("result") == false) {

                    } else {
                        JSONArray dataObj = jsonObj.getJSONArray("data");
                        int reviewNum = dataObj.length();
                        if (reviewNum == 0) {
                            showToast("没有啥评价");
                        }
                        reviews.addAll(GoodsReview.arrayGoodsReviewFromData(jsonObj.getString("data")));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
