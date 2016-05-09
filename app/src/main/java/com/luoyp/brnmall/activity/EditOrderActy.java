package com.luoyp.brnmall.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.adapter.EditOrderGoodsListAdapter;
import com.luoyp.brnmall.model.ShopCartModel;
import com.socks.library.KLog;

public class EditOrderActy extends BaseActivity {

    ShopCartModel shopCartModel;
    EditOrderGoodsListAdapter adapter;
    private android.widget.Button getAddress;
    private android.widget.ListView shopcarlistview;
    private TextView tvsum;
    private android.widget.Button btntijiandingdan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_order);
        this.btntijiandingdan = (Button) findViewById(R.id.btn_tijiandingdan);
        this.tvsum = (TextView) findViewById(R.id.tv_sum);
        this.shopcarlistview = (ListView) findViewById(R.id.shopcarlistview);
        this.getAddress = (Button) findViewById(R.id.getAddress);
        shopCartModel = App.shopCar;

        KLog.d(shopCartModel);
        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("填写订单");
        }
        adapter = new EditOrderGoodsListAdapter(this, shopCartModel);
        shopcarlistview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
