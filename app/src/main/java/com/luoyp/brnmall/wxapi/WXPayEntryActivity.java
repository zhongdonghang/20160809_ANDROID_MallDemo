package com.luoyp.brnmall.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.luoyp.brnmall.R;
import com.socks.library.KLog;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.simple.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private TextView tvResult;
    private Button btnReturn;

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        tvResult = (TextView) findViewById(R.id.tv_pay_result);
        btnReturn = (Button) findViewById(R.id.btn_return);

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

        api.handleIntent(getIntent(), this);

        btnReturn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Intent i = new Intent(WXPayEntryActivity.this,MainActivity.class);
                finish();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        //	Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            if (resp.errCode == 0) {//success
                EventBus.getDefault().post("wechat", "wechatrefreshorder");
                AlertDialog.Builder builder = new AlertDialog.Builder(WXPayEntryActivity.this);
                builder.setMessage("提示:订单状态可能会有延迟,请勿重复支付,在[我的订单]查看最新订单状态");

                builder.setTitle("恭喜!支付成功");
                builder.setCancelable(false);
                builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        WXPayEntryActivity.this.finish();
                    }
                });

                builder.create().show();
                EventBus.getDefault().post("", "wechatpaynotice");

            } else {
                KLog.d("支付失败：" + resp.errStr + ";code=" + String.valueOf(resp.errCode));

                AlertDialog.Builder builder = new AlertDialog.Builder(WXPayEntryActivity.this);
                builder.setMessage("获取微信支付信息失败,请再次尝试,或者更换支付方式");

                builder.setTitle("支付失败");
                builder.setCancelable(false);
                builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        WXPayEntryActivity.this.finish();
                    }
                });

                builder.create().show();

            }
        }
    }
}