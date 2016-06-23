package com.luoyp.brnmall.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.UserModel;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApplyActivity extends BaseActivity {

    private android.widget.EditText tixianjine;
    private android.widget.EditText zhanghao;
    private android.widget.EditText zhxinxi;
    private android.widget.EditText phone;
    private android.widget.Button btnapply;
    private android.widget.RelativeLayout activityapply;
    private TextView tvtxfs;
    private Button btntixianfangshi;
    private RelativeLayout payway;
    private int txfs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_apply);
        this.payway = (RelativeLayout) findViewById(R.id.payway);
        this.btntixianfangshi = (Button) findViewById(R.id.btntixianfangshi);
        this.tvtxfs = (TextView) findViewById(R.id.tvtxfs);
        this.activityapply = (RelativeLayout) findViewById(R.id.activity_apply);
        this.btnapply = (Button) findViewById(R.id.btn_apply);
        this.phone = (EditText) findViewById(R.id.phone);
        this.zhxinxi = (EditText) findViewById(R.id.zhxinxi);
        this.zhanghao = (EditText) findViewById(R.id.zhanghao);
        this.tixianjine = (EditText) findViewById(R.id.tixianjine);

        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("申请提现");
        }

        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply();
            }
        });
        btntixianfangshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> mAnimals = new ArrayList<String>();
                mAnimals.add("支付宝");
                mAnimals.add("银行卡");

                //Create sequence of items
                final CharSequence[] Animals = mAnimals.toArray(new String[mAnimals.size()]);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ApplyActivity.this);
                dialogBuilder.setTitle("选择提现方式");
                dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            txfs = 1;
                            tvtxfs.setText("提现方式:  支付宝");
                        } else {
                            txfs = 2;
                            tvtxfs.setText("提现方式:  银行卡");
                        }
                    }
                });
                //Create alert dialog object via builder
                AlertDialog alertDialogObject = dialogBuilder.create();
                alertDialogObject.setCanceledOnTouchOutside(false);
                alertDialogObject.setCancelable(false);
                //Show the dialog
                alertDialogObject.show();
            }
        });
    }

    public void apply() {
        if (tixianjine.getText().length() == 0) {
            showToast("请输入提现金额");
            return;
        }
        if (txfs == 0) {
            showToast("请选择提现方式");
            return;
        }
        if (zhanghao.getText().length() == 0) {
            showToast("请输入账号");
            return;
        }
        if (zhxinxi.getText().length() == 0) {
            showToast("请输入账户信息");
            return;
        }
        if (phone.getText().length() == 0) {
            showToast("请输入联系方式");
            return;
        }

        showProgressDialog("正在提交申请");

        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        String uid = String.valueOf(userModel.getUserInfo().getUid());

        BrnmallAPI.withdrawlApply(uid, tixianjine.getText().toString(), zhanghao.getText().toString(), zhxinxi.getText().toString(), txfs + "", phone.getText().toString(), new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                showToast("网络异常,请稍后再试");
            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getString("result").equals("true")) {
                        showToast(jsonObject.getJSONArray("data").getJSONObject(0).getString("msg"));

                        ApplyActivity.this.finish();
                        return;
                    }
                    showToast(jsonObject.getJSONArray("data").getJSONObject(0).getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
