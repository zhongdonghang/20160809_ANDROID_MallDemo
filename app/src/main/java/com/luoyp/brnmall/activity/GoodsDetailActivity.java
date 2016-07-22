package com.luoyp.brnmall.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.internal.Utils;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.SysUtils;
import com.luoyp.brnmall.adapter.GoodsDetailImagePagerAdapter;
import com.luoyp.brnmall.adapter.GoodsImageAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.GoodsDetailModel;
import com.luoyp.brnmall.model.UserModel;
import com.luoyp.brnmall.view.AutoScrollViewPager;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;
import com.tencent.stat.StatService;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GoodsDetailActivity extends BaseActivity {

    boolean isbuynow = false;
    TextView topbarTitle;
    private ImageView goodsIcon;
    private TextView goodsName, goodsPrice, goodsPinPai, goodsGuiGe;
    //  private ListView listView;
    private GoodsImageAdapter adapter;
    private GoodsDetailModel goodsDetailModel;
    private String pid, uid;
    private boolean isLogin = false;
    private boolean isFavorite = false;
    private String sid = "";
    private com.luoyp.brnmall.view.AutoScrollViewPager autoviewpager;
    private TextView tvstore;
    private TextView tvfavorite;
    private android.widget.Button btnbuynow;
    private android.widget.Button btnaddtocart;
    private GoodsDetailImagePagerAdapter homeAdAdapter;
    private List<String> imageIdList;
    private android.widget.ScrollView goodsscrollview;
    private TextView goodsname;
    private TextView goodsprice;
    private TextView goodspinpai;
    private TextView goodsguige;
    private TextView goodpingjia;
    private android.widget.LinearLayout bottomLL;
    private TextView goodmarketsprice;
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_detail);
        this.web = (WebView) findViewById(R.id.web);
        this.goodmarketsprice = (TextView) findViewById(R.id.goodmarketsprice);
        this.bottomLL = (LinearLayout) findViewById(R.id.bottomLL);
        this.goodpingjia = (TextView) findViewById(R.id.goodpingjia);
        this.goodsguige = (TextView) findViewById(R.id.goodsguige);
        this.goodspinpai = (TextView) findViewById(R.id.goodspinpai);
        this.goodsprice = (TextView) findViewById(R.id.goodsprice);
        this.goodsname = (TextView) findViewById(R.id.goodsname);
        this.goodsscrollview = (ScrollView) findViewById(R.id.goodsscrollview);
        this.btnaddtocart = (Button) findViewById(R.id.btn_addtocart);
        this.btnbuynow = (Button) findViewById(R.id.btn_buynow);
        this.tvfavorite = (TextView) findViewById(R.id.tv_favorite);
        this.tvstore = (TextView) findViewById(R.id.tv_store);
        this.autoviewpager = (AutoScrollViewPager) findViewById(R.id.auto_view_pager);

        goodsscrollview.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                goodsscrollview.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        LayoutInflater mInflater = LayoutInflater.from(this);
        View view = mInflater.inflate(R.layout.goods_detail_head_view, null);
        goodsIcon = (ImageView) view.findViewById(R.id.iv_goods_icon);
        goodsName = (TextView) view.findViewById(R.id.tv_goods_name);
        goodsPrice = (TextView) view.findViewById(R.id.tv_goods_price);
        goodsPinPai = (TextView) view.findViewById(R.id.tv_goods_pinpai);
        goodsGuiGe = (TextView) view.findViewById(R.id.tv_goods_guige);
        // listView.addHeaderView(view);

        goodsDetailModel = new GoodsDetailModel();

        //接收Intent传递过来的数据
        Intent mIntent = getIntent();
        String name = mIntent.getStringExtra("name");
        pid = mIntent.getStringExtra("pid");

        // 设置topbar
        topbarTitle = (TextView) findViewById(R.id.topbar_title);
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
        Properties prop = new Properties();
        prop.setProperty("pid", pid);

        //   prop.setProperty("uid", uid);
        StatService.trackCustomKVEvent(GoodsDetailActivity.this, "OnViewGoods", prop);
    }

    // 点击加入购物车
    public void addCart(View view) {
        if (!checkLogin()) {
            return;
        }
        // 获取当前用户的uid
        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        uid = String.valueOf(userModel.getUserInfo().getUid());
        showProgressDialog("添加到购物车");
        addGoodsToCart(pid, uid, "1");
    }

    // 点击加入购物车
    public void buynow(View view) {
        isbuynow = true;
        showProgressDialog("正在提交商品信息");
        addGoodsToCart(pid, uid, "1");
    }

    // 点击加入收藏，或取消收藏
    public void addFavorite(View view) {
        if (!checkLogin()) {
            return;
        }
        // 获取当前用户的uid
        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        uid = String.valueOf(userModel.getUserInfo().getUid());
        if (isFavorite) {
            deleteFavorite(pid, uid);
        } else {
            addGoodsToFavorite(pid, uid);
        }
    }

    // 打开店铺
    public void toStore(View view) {
        if (TextUtils.isEmpty(sid)) {
            return;
        }
        Intent intent = new Intent(this, StoreActivity.class);
        intent.putExtra("sid", sid);
        startActivity(intent);
    }

    private void setupListView() {
        goodsDetailModel.setImageBeanList(new ArrayList<GoodsDetailModel.ImageBean>());
        adapter = new GoodsImageAdapter(this, goodsDetailModel);
        //  listView.setAdapter(adapter);
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

                    imageIdList = new ArrayList<String>();

                    for (int i = 0; i < goodsDetailModel.getImageBeanList().size(); i++) {
                        imageIdList.add(BrnmallAPI.BaseImgUrl1 + goodsDetailModel.getGoodsInfo().getStoreId()
                                + BrnmallAPI.BaseImgUrl3 + goodsDetailModel.getImageBeanList().get(i).getShowImg());
                    }
                    homeAdAdapter = new GoodsDetailImagePagerAdapter(GoodsDetailActivity.this, imageIdList, false);
                    autoviewpager.setAdapter(homeAdAdapter.setInfiniteLoop(true));
                    autoviewpager.setOnPageChangeListener(new MyOnPageChangeListener());
                    autoviewpager.setInterval(3500);
                    autoviewpager.startAutoScroll();
                    autoviewpager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % Utils.getSize(imageIdList));

                    homeAdAdapter.notifyDataSetChanged();

                    sid = goodsDetailModel.getGoodsInfo().getStoreId() + "";
                    App.getPicasso().load(BrnmallAPI.BaseImgUrl1 + goodsDetailModel.getGoodsInfo().getStoreId()
                            + BrnmallAPI.BaseImgUrl3 + goodsDetailModel.getGoodsInfo().getShowImg())
                            .placeholder(R.drawable.goodsdefaulimg).error(R.drawable.goodsdefaulimg).into(goodsIcon);

                    web.loadData(goodsDetailModel.getGoodsInfo().getDescription(), "text/html", "UTF-8");
                    goodsname.setText(goodsDetailModel.getGoodsInfo().getName());
                    if (isLogin) {
                        String price = SysUtils.formatDouble((Double.valueOf(App.getPref("zhekou", "10")) * goodsDetailModel.getGoodsInfo().getShopPrice()) * 10 / 100);
                        goodsPrice.setText(" ￥ " + price + " (" + App.getPref("zhekoutitle", "") + ")");
                    } else {
                        goodsPrice.setText(" ￥ " + goodsDetailModel.getGoodsInfo().getShopPrice());
                    }
                    goodsName.setText(goodsDetailModel.getGoodsInfo().getName());
                    goodsprice.setText("商城价￥ " + goodsDetailModel.getGoodsInfo().getShopPrice());
                    goodmarketsprice.setText("市场价￥ " + goodsDetailModel.getGoodsInfo().getMarketPrice());
                    goodmarketsprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    goodspinpai.setText("品牌  " + goodsDetailModel.getBrandInfo().getName());
                    goodsguige.setText("规格  ");
                    goodpingjia.setText("评价 (" + goodsDetailModel.getGoodsInfo().getReviewCount() + ")");
                    topbarTitle.setText(goodsDetailModel.getGoodsInfo().getName());
                    adapter.notifyDataSetChanged();
                    bottomLL.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //查看评论
    public void viewComment(View view) {
        KLog.d("pid = " + goodsDetailModel.getGoodsInfo().getPid());
        Intent intent = new Intent(this, GoodsReviewActivity.class);
        intent.putExtra("pid", goodsDetailModel.getGoodsInfo().getPid() + "");
        startActivity(intent);
    }

    /**
     * 加入购物车
     *
     * @param pid   商品id
     * @param uid   用户id
     * @param count 数量
     */
    private void addGoodsToCart(String pid, String uid, String count) {

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
                        if (isbuynow) {
                            isbuynow = false;
                            GoodsDetailActivity.this.finish();
                            EventBus.getDefault().post("--", "buynowClick");
                            return;

                        }
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

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
//            indexText.setText(new StringBuilder().append((position) % ListUtils.getSize(imageIdList) + 1).append("/")
//                    .append(ListUtils.getSize(imageIdList)));
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }


}
