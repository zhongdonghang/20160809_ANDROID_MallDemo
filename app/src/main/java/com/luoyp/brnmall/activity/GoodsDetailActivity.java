package com.luoyp.brnmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.adapter.GoodsImageAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.GoodsDetailModel;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GoodsDetailActivity extends BaseActivity {

    private ImageView goodsIcon;
    private TextView goodsName,goodsPrice,goodsPinPai,goodsGuiGe;
    private ListView listView;
    private GoodsImageAdapter adapter;

    private GoodsDetailModel goodsDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_detail);

        goodsIcon = (ImageView) findViewById(R.id.iv_goods_icon);
        goodsName = (TextView) findViewById(R.id.tv_goods_name);
        goodsPrice = (TextView) findViewById(R.id.tv_goods_price);
        goodsPinPai = (TextView) findViewById(R.id.tv_goods_pinpai);
        goodsGuiGe = (TextView) findViewById(R.id.tv_goods_guige);
        listView = (ListView) findViewById(R.id.lv_goods_image);

        goodsDetailModel = new GoodsDetailModel();

        //接收Intent传递过来的数据
        Intent mIntent = getIntent();
        String name = mIntent.getStringExtra("name");
        String pid = mIntent.getStringExtra("pid");

        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("商品详情");
        }

        setupListView();

        // 加载商品信息
        getGoodsData(pid);
    }

    private void setupListView(){
        goodsDetailModel.setImageBeanList(new ArrayList<GoodsDetailModel.ImageBean>());
        adapter = new GoodsImageAdapter(this, goodsDetailModel.getImageBeanList());
        listView.setAdapter(adapter);
    }

    private void getGoodsData(String pid){
        showProgressDialog("正在加载");
        BrnmallAPI.getProductDetail(pid, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                showToast("网络正开小差，请稍后再试");
            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                KLog.json("商品详情=  " + response);
                if (TextUtils.isEmpty(response)){
                    return;
                }

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ("false".equals(jsonObject.getString("result"))){
                        return;
                    }

                    goodsDetailModel = goodsDetailModel.jsonToGoodsDetailModel(jsonObject.getString("data"));

                    App.getPicasso().load(BrnmallAPI.BaseImgUrl1 + goodsDetailModel.getGoodsInfo().getStoreId()
                            +BrnmallAPI.BaseImgUrl3+ goodsDetailModel.getGoodsInfo().getShowImg())
                            .placeholder(R.mipmap.logo).error(R.mipmap.logo).into(goodsIcon);

                    goodsName.setText(goodsDetailModel.getGoodsInfo().getName());
                    goodsPrice.setText("￥"+goodsDetailModel.getGoodsInfo().getShopPrice());
                    goodsPinPai.setText(goodsDetailModel.getBrandInfo().getName());

                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
