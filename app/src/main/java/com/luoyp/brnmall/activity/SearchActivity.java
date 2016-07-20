package com.luoyp.brnmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.adapter.CategoryGoodsAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.CategoryGoodsModel;
import com.luoyp.brnmall.model.CategoryModel;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    int index = 1;
    private android.widget.ImageButton topbarleft;
    private android.widget.EditText searchEt;
    private android.widget.ImageView searchIv;
    private android.widget.RelativeLayout searbar;
    private android.widget.ListView searchlistView;
    private android.support.v4.widget.SwipeRefreshLayout swipe;
    private List<CategoryModel.Category> categoryListData;
    private CategoryGoodsModel categoryGoodsModel;
    private CategoryGoodsAdapter categoryGoodsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        this.swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        this.searchlistView = (ListView) findViewById(R.id.searchlistView);
        this.searbar = (RelativeLayout) findViewById(R.id.searbar);
        this.searchIv = (ImageView) findViewById(R.id.searchIv);
        this.searchEt = (EditText) findViewById(R.id.searchEt);
        this.topbarleft = (ImageButton) findViewById(R.id.topbar_left);
        searchEt.setText(getIntent().getStringExtra("key"));

        categoryGoodsModel = new CategoryGoodsModel();
        categoryGoodsModel.setGoodsBeanList(new ArrayList<CategoryGoodsModel.GoodsBean>());


        categoryListData = new ArrayList<>();
        categoryGoodsAdapter = new CategoryGoodsAdapter(this, categoryGoodsModel);
        searchlistView.setAdapter(categoryGoodsAdapter);


        searchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipe.setRefreshing(true);
                categoryGoodsModel.getGoodsBeanList().clear();
                index = 1;
                searching();
            }
        });
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                categoryGoodsModel.getGoodsBeanList().clear();
                index = 1;
                searching();
            }
        });
        searchlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, GoodsDetailActivity.class);
                intent.putExtra("pid", categoryGoodsModel.getGoodsBeanList().get(position).getPid() + "");
                intent.putExtra("name", categoryGoodsModel.getGoodsBeanList().get(position).getName());
                startActivity(intent);
            }
        });
        swipe.setRefreshing(true);
        searching();
    }

    public void searching() {
        BrnmallAPI.MallSearch(searchEt.getText().toString(), index + "", new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                // 关闭加载提示
                showToast("网络异常,请稍后再试吧");
                dismissProgressDialog();
                swipe.setRefreshing(false);

            }

            @Override
            public void onResponse(String response) {
                // 关闭加载提示
                dismissProgressDialog();
                swipe.setRefreshing(false);

                KLog.json("分类商品=  " + response);
                if (TextUtils.isEmpty(response)) {
                    showToast("没有找到相关商品");
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    if (jsonObject.getString("result").equals("false") || dataObject.getJSONArray("ProductList").length() == 0) {
                        showToast("没有相关的商品了");
                        return;
                    }

                    categoryGoodsModel.setPageInfo(categoryGoodsModel.jsonToPageInfoBean(dataObject.getString("PageModel")));
                    categoryGoodsModel.getGoodsBeanList().addAll(categoryGoodsModel.jsonToGoodsBeanList(dataObject.getString("ProductList")));
                    categoryGoodsAdapter.notifyDataSetChanged();
                    index++;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
