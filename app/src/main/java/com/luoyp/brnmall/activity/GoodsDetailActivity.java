package com.luoyp.brnmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.adapter.GoodsImageAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.GoodsDetailModel;
import com.luoyp.brnmall.model.UserModel;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GoodsDetailActivity extends BaseActivity {

    private ImageView goodsIcon;
    private TextView goodsName, goodsPrice, goodsPinPai, goodsGuiGe;
    private ListView listView;
    private GoodsImageAdapter adapter;

    private GoodsDetailModel goodsDetailModel;

    private String pid, uid;
    private boolean isLogin = false;
    private boolean isFavorite = false;

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
        pid = mIntent.getStringExtra("pid");

        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText(name);
        }

        setupListView();

        // 加载商品信息
        getGoodsData(pid);

        isLogin = App.getPref("isLogin", false);
        if (isLogin) {
            // 获取当前用户的uid
            UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
            uid = String.valueOf(userModel.getUserInfo().getUid());
        }
    }

    // 点击加入购物车
    public void addCart(View view) {
        if (!isLogin) return;
        addGoodsToCart(pid, uid, "1");
    }

    // 点击加入收藏，或取消收藏
    public void addFavorite(View view) {
        if (!isLogin) return;
        if (isFavorite) {
            deleteFavorite(pid, uid);
        } else {
            addGoodsToFavorite(pid, uid);
        }
    }


    private void setupListView() {
        goodsDetailModel.setImageBeanList(new ArrayList<GoodsDetailModel.ImageBean>());
        adapter = new GoodsImageAdapter(this, goodsDetailModel);
        listView.setAdapter(adapter);
    }

    private void getGoodsData(String pid) {
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
                if (TextUtils.isEmpty(response)) {
                    return;
                }

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ("false".equals(jsonObject.getString("result"))) {
                        return;
                    }
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    goodsDetailModel.setGoodsInfo(new Gson().fromJson(dataObject.getString("ProductInfo")
                            , GoodsDetailModel.GoodsBean.class));
                    goodsDetailModel.setBrandInfo(new Gson().fromJson(dataObject.getString("BrandInfo")
                            , GoodsDetailModel.BrandBean.class));
                    goodsDetailModel.getImageBeanList().addAll(goodsDetailModel.jsonToImageBeanList(dataObject.getString("ProductImageList")));

                    App.getPicasso().load(BrnmallAPI.BaseImgUrl1 + goodsDetailModel.getGoodsInfo().getStoreId()
                            + BrnmallAPI.BaseImgUrl3 + goodsDetailModel.getGoodsInfo().getShowImg())
                            .placeholder(R.mipmap.logo).error(R.mipmap.logo).into(goodsIcon);

                    goodsName.setText(goodsDetailModel.getGoodsInfo().getName());
                    goodsPrice.setText("￥" + goodsDetailModel.getGoodsInfo().getShopPrice());
                    goodsPinPai.setText(goodsDetailModel.getBrandInfo().getName());

                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 加入购物车
     *
     * @param pid   商品id
     * @param uid   用户id
     * @param count 数量
     */
    private void addGoodsToCart(String pid, String uid, String count) {
        showProgressDialog("正在添加到购物车");
        BrnmallAPI.addProductToCart(pid, uid, count, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                showToast("网络异常,请稍后再试吧");
            }

            @Override
            public void onResponse(String response) {
                //  KLog.json(response);
                dismissProgressDialog();
                if (response != null && !TextUtils.isEmpty(response)) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        showToast(jsonObject.getJSONArray("data").getJSONObject(0).getString("msg"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 加入收藏
     *
     * @param pid 商品id
     * @param uid 用户id
     */
    private void addGoodsToFavorite(String pid, String uid) {
        showProgressDialog("正在收藏商品");
        BrnmallAPI.addProductToFavorite(pid, uid, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                showToast("网络异常,请稍后再试吧");
            }

            @Override
            public void onResponse(String response) {
                //     KLog.json(response);
                dismissProgressDialog();
                if (response != null && !TextUtils.isEmpty(response)) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        showToast(jsonObject.getJSONArray("data").getJSONObject(0).getString("msg"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 取消收藏
     *
     * @param pid 商品id
     * @param uid 用户id
     */
    private void deleteFavorite(String pid, String uid) {
        showProgressDialog("正在取消收藏");
        BrnmallAPI.deleteFavoriteProduct(pid, uid, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                showToast("网络异常,请稍后再试吧");
            }

            @Override
            public void onResponse(String response) {
                //   KLog.json(response);
                dismissProgressDialog();
                if (response != null && !TextUtils.isEmpty(response)) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        showToast(jsonObject.getJSONArray("data").getJSONObject(0).getString("msg"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}
