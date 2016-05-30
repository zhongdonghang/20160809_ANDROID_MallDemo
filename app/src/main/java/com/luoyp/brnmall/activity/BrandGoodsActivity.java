package com.luoyp.brnmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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

public class BrandGoodsActivity extends BaseActivity {

    private String brandName = "";
    private String brandID = "";
    private String cateId = "";

    private com.handmark.pulltorefresh.library.PullToRefreshListView brandgoodslistview;

    private List<CategoryModel.Category> categoryListData;
    private CategoryGoodsModel categoryGoodsModel;
    private CategoryGoodsAdapter categoryGoodsAdapter;
    private int pageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_brand_goods);

        categoryGoodsModel = new CategoryGoodsModel();
        categoryGoodsModel.setGoodsBeanList(new ArrayList<CategoryGoodsModel.GoodsBean>());

        this.brandgoodslistview = (PullToRefreshListView) findViewById(R.id.brand_goods_list_view);
        brandName = getIntent().getStringExtra("name");
        brandID = getIntent().getStringExtra("brandId");
        cateId = getIntent().getStringExtra("cateId");

        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText(brandName);
        }
        // 设置适配器
        categoryGoodsAdapter = new CategoryGoodsAdapter(this, categoryGoodsModel);
        brandgoodslistview.setAdapter(categoryGoodsAdapter);

        // 设置刷新方式
        brandgoodslistview.setMode(PullToRefreshBase.Mode.BOTH);
        brandgoodslistview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉
                pageNumber = 1;
                categoryGoodsModel.getGoodsBeanList().clear();
                getCategoryCoodsList(pageNumber + "");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getCategoryCoodsList(pageNumber + "");
            }
        });

        brandgoodslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KLog.d("产品id" + categoryGoodsModel.getGoodsBeanList().get(position - 1).getPid());
                Intent intent = new Intent(BrandGoodsActivity.this, GoodsDetailActivity.class);
                intent.putExtra("pid", categoryGoodsModel.getGoodsBeanList().get(position - 1).getPid() + "");
                intent.putExtra("name", categoryGoodsModel.getGoodsBeanList().get(position - 1).getName());
                startActivity(intent);
            }
        });
        showProgressDialog("正在加载数据");
        getCategoryCoodsList("1");

    }

    /**
     * 获取分类商品
     */
    private void getCategoryCoodsList(String pageIndex) {
        KLog.d(cateId + "  XXXXXXXXXXXXXX  " + brandID);
        BrnmallAPI.getProductListByBrandId(cateId, brandID, pageIndex, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                // 关闭加载提示
                showToast("网络异常,请稍后再说吧");
                dismissProgressDialog();
                brandgoodslistview.onRefreshComplete();
                categoryGoodsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onResponse(String response) {
                // 关闭加载提示
                dismissProgressDialog();
                brandgoodslistview.onRefreshComplete();
                KLog.json("品牌商品=  " + response);
                if (TextUtils.isEmpty(response)) {
                    showToast("没有找到相关商品");
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    if (jsonObject.getString("result").equals("false") || dataObject.getJSONArray("ProductList").length() == 0) {
                        showToast("暂时没有相关商品");
                        return;
                    }

                    categoryGoodsModel.setPageInfo(categoryGoodsModel.jsonToPageInfoBean(dataObject.getString("PageModel")));
                    categoryGoodsModel.getGoodsBeanList().addAll(categoryGoodsModel.jsonToGoodsBeanList(dataObject.getString("ProductList")));
                    categoryGoodsAdapter.notifyDataSetChanged();
                    pageNumber++;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
