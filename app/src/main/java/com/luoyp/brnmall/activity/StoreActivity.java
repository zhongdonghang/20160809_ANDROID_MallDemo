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
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.adapter.StoreGoodsAdapter;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.CategoryGoodsModel;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoreActivity extends BaseActivity {

    private CircleImageView storeIcon;
    private TextView storeName, storePhone, storeQQ;
    private PullToRefreshListView listView;

    private List<CategoryGoodsModel.GoodsBean> goodsList;
    private StoreGoodsAdapter adapter;
    private String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_store);
        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("店铺详情");
        }

        storeIcon = (CircleImageView) this.findViewById(R.id.store_icon);
        storeName = (TextView) this.findViewById(R.id.store_name);
        storePhone = (TextView) this.findViewById(R.id.store_phone);
        storeQQ = (TextView) this.findViewById(R.id.store_qq);
        listView = (PullToRefreshListView) this.findViewById(R.id.store_goods_listview);

        setupListView();// 设置ListView

        EventBus.getDefault().register(this);
        sid = getIntent().getStringExtra("sid");
        loadStoreInfo(sid);// 获取店铺信息

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscriber(tag = "store_add_tocart")
    public void addToCart(String pid) {
        KLog.d(pid);
        if (!checkLogin()) {
            return;
        }

        String uid = App.getPref("uid","");
        if (uid == null || uid.isEmpty()) {
            checkLogin();
        } else {
            addGoodsToCart(pid, uid, "1");
        }
    }

    private void setupListView(){
        goodsList = new ArrayList<CategoryGoodsModel.GoodsBean>();
        adapter = new StoreGoodsAdapter(this,goodsList);
        listView.setAdapter(adapter);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                goodsList.clear();
                loadStoreGoods(sid);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StoreActivity.this, GoodsDetailActivity.class);
                intent.putExtra("name", goodsList.get(position-1).getName());
                intent.putExtra("pid", goodsList.get(position-1).getPid() + "");
                startActivity(intent);
            }
        });
    }

    private void loadStoreInfo(String id) {
        BrnmallAPI.getStoreInfo(id, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
            }

            @Override
            public void onResponse(String response) {
                KLog.json("店铺信息  " + response);
                if (TextUtils.isEmpty(response)) {
                    showToast("请稍后再试");
                    return;
                }
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    JSONObject dataObj = jsonObj.getJSONObject("data");
                    if (jsonObj.getBoolean("result") == false) {

                    } else {
                        storeName.setText(dataObj.getString("Name"));
                        storePhone.setText("电话:"+dataObj.getString("Mobile"));
                        storeQQ.setText("QQ:"+dataObj.getString("QQ"));
                        App.getPicasso().load(BrnmallAPI.BaseImgUrl1+dataObj.getString("Logo"))
                                .placeholder(R.drawable.goodsdefaulimg).error(R.drawable.goodsdefaulimg)
                                .into(storeIcon);
                        loadStoreGoods(sid);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取店铺商品
     * @param id
     */
    private void loadStoreGoods(String id) {
        BrnmallAPI.getStoreGoods(id, new ApiCallback<String>() {
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
                KLog.json("店铺商品  "+response);
                if (TextUtils.isEmpty(response)) {
                    showToast("请稍后再试");
                    return;
                }
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    JSONObject dataObj = jsonObj.getJSONObject("data");
                    if (jsonObj.getBoolean("result") == false) {

                    } else {
                        goodsList.addAll(CategoryGoodsModel.jsonToGoodsBeanList(dataObj.getString("ProductList")));
                        adapter.notifyDataSetChanged();
                    }
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


}
