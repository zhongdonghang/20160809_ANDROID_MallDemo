package com.luoyp.brnmall.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.UserModel;

public class MyProfileActivity extends BaseActivity {

    private android.widget.ImageView ivavatar;
    private android.widget.RelativeLayout reavatar;
    private TextView tvnicheng;
    private TextView tvnickname;
    private android.widget.RelativeLayout renicheng;
    private TextView tvrealname;
    private TextView tvzhenming;
    private android.widget.RelativeLayout rerealname;
    private TextView tvtempsex;
    private TextView tvsex;
    private android.widget.RelativeLayout resex;
    private TextView tvtempregion;
    private TextView tvsfz;
    private android.widget.RelativeLayout reregion;
    private TextView tvtempsign;
    private TextView tvjianjie;
    private android.widget.RelativeLayout resign;
    private android.widget.RelativeLayout relogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_profile);
        this.relogout = (RelativeLayout) findViewById(R.id.re_logout);
        this.resign = (RelativeLayout) findViewById(R.id.re_sign);
        this.tvjianjie = (TextView) findViewById(R.id.tv_jianjie);
        this.tvtempsign = (TextView) findViewById(R.id.tv_temp_sign);
        this.reregion = (RelativeLayout) findViewById(R.id.re_region);
        this.tvsfz = (TextView) findViewById(R.id.tv_sfz);
        this.tvtempregion = (TextView) findViewById(R.id.tv_temp_region);
        this.resex = (RelativeLayout) findViewById(R.id.re_sex);
        this.tvsex = (TextView) findViewById(R.id.tv_sex);
        this.tvtempsex = (TextView) findViewById(R.id.tv_temp_sex);
        this.rerealname = (RelativeLayout) findViewById(R.id.re_realname);
        this.tvzhenming = (TextView) findViewById(R.id.tv_zhenming);
        this.tvrealname = (TextView) findViewById(R.id.tv_realname);
        this.renicheng = (RelativeLayout) findViewById(R.id.re_nicheng);
        this.tvnickname = (TextView) findViewById(R.id.tv_nickname);
        this.tvnicheng = (TextView) findViewById(R.id.tv_nicheng);
        this.reavatar = (RelativeLayout) findViewById(R.id.re_avatar);
        this.ivavatar = (ImageView) findViewById(R.id.iv_avatar);

        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        tvnickname.setText(userModel.getUserInfo().getNickName());
        tvzhenming.setText(userModel.getUserInfo().getRealName());
        if (userModel.getUserInfo().getGender() == 0) {
            tvsex.setText("其他");
        }
        if (userModel.getUserInfo().getGender() == 1) {
            tvsex.setText("男");
        }
        if (userModel.getUserInfo().getGender() == 2) {
            tvsex.setText("女");
        }

        tvsfz.setText(userModel.getUserInfo().getIdCard());
        tvjianjie.setText(userModel.getUserInfo().getBio());

        App.getPicasso().load(BrnmallAPI.userImgUrl + App.getPref("avatar", "")).error(R.mipmap.logo).placeholder(R.mipmap.logo).into(ivavatar);

        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("个人中心");
        }
        relogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyProfileActivity.this);
                builder.setMessage("确定注销当前用户?");

                builder.setTitle("退出登录");
                builder.setCancelable(false);
                builder.setPositiveButton("我点错了", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("注销用户", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        App.setPref("isLogin", false);
                        App.setPref("nicheng", "");
                        App.setPref("zhenming", "");
                        App.setPref("sex", -1);
                        App.setPref("sfz", "");
                        App.setPref("jianjie", "");
                        App.setPref("avatar", "");
                        MyProfileActivity.this.finish();
                    }
                });
                builder.create().show();
            }
        });
    }
}
