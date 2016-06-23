package com.luoyp.brnmall.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.SysUtils;

public class TixianDetailActivity extends BaseActivity {

    private TextView tvje;
    private TextView tvtixianjine;
    private TextView tvs;
    private TextView tvstate;
    private TextView tvzh;
    private TextView tvzhanghao;
    private TextView tvzhxx;
    private TextView tvzhanghaoxinxi;
    private TextView tvt1;
    private TextView tvshenqingshijian;
    private TextView tvps;
    private TextView tvpishi;
    private TextView tvpst;
    private TextView tvpishishijian;
    private android.widget.RelativeLayout activitytixiandetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tixian_detail);
        this.activitytixiandetail = (RelativeLayout) findViewById(R.id.activity_tixian_detail);
        this.tvpishishijian = (TextView) findViewById(R.id.tv_pishishijian);
        this.tvpst = (TextView) findViewById(R.id.tv_pst);
        this.tvpishi = (TextView) findViewById(R.id.tv_pishi);
        this.tvps = (TextView) findViewById(R.id.tv_ps);
        this.tvshenqingshijian = (TextView) findViewById(R.id.tv_shenqingshijian);
        this.tvt1 = (TextView) findViewById(R.id.tv_t1);
        this.tvzhanghaoxinxi = (TextView) findViewById(R.id.tv_zhanghaoxinxi);
        this.tvzhxx = (TextView) findViewById(R.id.tv_zhxx);
        this.tvzhanghao = (TextView) findViewById(R.id.tv_zhanghao);
        this.tvzh = (TextView) findViewById(R.id.tv_zh);
        this.tvstate = (TextView) findViewById(R.id.tv_state);
        this.tvs = (TextView) findViewById(R.id.tv_s);
        this.tvtixianjine = (TextView) findViewById(R.id.tv_tixianjine);
        this.tvje = (TextView) findViewById(R.id.tv_je);

        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("申请提现详情");
        }
        tvtixianjine.setText(App.tixianLogModel.getApplyAmount() + "");
        if (App.tixianLogModel.getState() == 1) {
            tvstate.setText("申请中");
        }
        if (App.tixianLogModel.getState() == 2) {
            tvstate.setText("不通过");
        }
        if (App.tixianLogModel.getState() == 3) {
            tvstate.setText("已通过");
        }
        if (App.tixianLogModel.getPayType() == 1) {
            tvzhanghao.setText("支付宝: " + App.tixianLogModel.getPayAccount().replace(" ", ""));

        }
        if (App.tixianLogModel.getPayType() == 2) {
            tvzhanghao.setText("银行卡: " + App.tixianLogModel.getPayAccount().replace(" ", ""));

        }
        tvzhanghaoxinxi.setText(App.tixianLogModel.getApplyRemark().replace(" ", ""));
        tvshenqingshijian.setText(SysUtils.getDate(App.tixianLogModel.getApplyTime()));
        tvpishi.setText("支付单号: " + App.tixianLogModel.getReason().replace(" ", ""));
        tvpishishijian.setText(SysUtils.getDate(App.tixianLogModel.getOperatTime()));

    }
}
