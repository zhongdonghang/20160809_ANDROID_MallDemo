package com.luoyp.brnmall.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Xml;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.alipay.PayResult;
import com.luoyp.brnmall.alipay.SignUtils;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.wepay.PrePayModel;
import com.luoyp.brnmall.wxapi.Constants;
import com.luoyp.brnmall.wxapi.MD5;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class PayActivity extends BaseActivity {
    // 商户PID
    public static final String PARTNER = "2088221484266786";
    // 商户收款账号
    public static final String SELLER = "qtb2016@163.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALm2jbSa1rCpgyfsPTdkQsR1mMmG9WqSq2rhJn4LXu/s7tpSHSTmr00od93LjQA6JGykK80tcVq1fFnz0XRkpsDb8YP1y4QbE28Cgh9yiuFQx/W5HRfBHa/CQOqmF+PdHpanMsJCWEV71StTthClf6VctDOmgqKpb4rldfApDoahAgMBAAECgYEAihNWbE8rDBIcN5SHNyXOFm8wd7VlxiTiWgaoLdKadVv9gkjG7matM3rBFCCA5whTiIrPHi+JNd31ZJPIyPcEmt1ZOw9WUeqjQ4bYqWdYl/UHuwFhUQIC2RsW59NuzISn+3i9NpKPLVjVhKKU0iOdNwiwCsIqg0lpJXVaKz6GkCECQQDylDHwn7A+xZ/vt8skeFaDW0B9ReiBb2craOqweOgkTM4Ri/fRHHOkzcNBKVeCV/VCUXfbEGsM93/uKx1ItXS9AkEAw/zuRxxKYf+JL5Sfei8Vuipkt7bvLRU2I1JWLHUm+YNSU6NpMcSsRlU1Z1Q4JF3niCo0cYZXqQNMHICL8gtBtQJATSIwSwoL+bnPZGM11g/punT+qZbcGqQ40wXWcmzPrBM8BzpRf42jfAjtiD/EEq8zTnYnPWIYGBRu+mV9N0xzpQJAW2O2OLKYfNoLvoQvWWpbV1QtYv2Kyhr6A76BMHnwkqkJ2rZ4dxyeuK1DGcvL4ilnrbcAfW+HsOg4tZG7sEJPgQJBAOLf+Z62TNA94PFxq2MgsvwWXebDEdR+nEZqeUlOU7Rb0emZAmvnHcIDvPRNsoL8R+tIdR00iAcKB8N2Fo8fAlc=";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    private static final int SDK_PAY_FLAG = 1;
    PayReq req;
    IWXAPI msgApi;
    private String oid = "";
    private String osn = "";
    private String price = "";
    private PrePayModel prePayModel;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        //  Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post("", "refreshorder");
                        AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                        builder.setMessage("提示:订单状态可能会有延迟,请勿重复支付,在[我的订单]查看最新订单状态");

                        builder.setTitle("恭喜!支付成功");
                        builder.setCancelable(false);
                        builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                PayActivity.this.finish();
                            }
                        });
                        builder.create().show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            //    Toast.makeText(PayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                            builder.setMessage("提示:订单状态可能会有延迟,请勿重复支付,在[我的订单]查看最新订单状态");

                            builder.setTitle("支付结果确认中");
                            builder.setCancelable(false);
                            builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.create().show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            //Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                            builder.setMessage("请稍后再试,或者更换支付方式");

                            builder.setTitle("支付失败");
                            builder.setCancelable(false);
                            builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.create().show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pay);
        EventBus.getDefault().register(this);
        msgApi = WXAPIFactory.createWXAPI(getApplicationContext(), Constants.APP_ID, true);
        msgApi.registerApp(Constants.APP_ID);
        req = new PayReq();
        prePayModel = new PrePayModel();

        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("精生缘收银台");
        }
        oid = getIntent().getStringExtra("oid");
        osn = getIntent().getStringExtra("osn");
        price = getIntent().getStringExtra("price");
        KLog.d("订单信息 oid" + oid + "   osn  " + osn + "   price  " + price);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }

    @Subscriber(tag = "wechatpaynotice")
    public void noti(String s) {
        finish();
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void alipay(View v) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }
        String orderInfo = getOrderInfo(osn, "APP购物-Android", price);
        //  KLog.d("提交支付宝info" + orderInfo);

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(PayActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v
     */
    public void h5Pay(View v) {
//        Intent intent = new Intent(this, H5PayDemoActivity.class);
//        Bundle extras = new Bundle();
//        /**
//         * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
//         * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
//         * 商户可以根据自己的需求来实现
//         */
//        String url = "http://m.meituan.com";
//        // url可以是一号店或者美团等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
//        extras.putString("url", url);
//        intent.putExtras(extras);
//        startActivity(intent);

    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + BrnmallAPI.BaseIP + "/Alipay/Notify" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 10);
        return oid + "A" + key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    public void wechatpay(View view) {
        loadPrepay();
    }

    private void loadPrepay() {
        showProgressDialog("正在请求支付信息");
        String xml = genProductArgs();
        BrnmallAPI.createWechatPrepay(xml, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                showToast("网络异常,请稍后再试");
            }

            @Override
            public void onResponse(String response) {

                // KLog.d("生成预付返回xml " + response);
                if (response == null || TextUtils.isEmpty(response)) {
                    dismissProgressDialog();
                    showToast("支付异常,请稍后再试吧");
                    return;
                }
                pasexml(response.replace(" ", ""));
                if (!prePayModel.getSuccess()) {
                    dismissProgressDialog();
                    showToast("支付异常,请稍后再试吧");
                    return;
                }
                if (!msgApi.isWXAppInstalled()) {
                    dismissProgressDialog();
                    showToast("请检查是否已安装微信");
                    return;
                }
                if (!msgApi.isWXAppSupportAPI()) {
                    dismissProgressDialog();
                    showToast("微信版本不支持,请升级微信到最新版本");
                    return;
                }

                req.appId = Constants.APP_ID;
                req.partnerId = Constants.MCH_ID;
                req.prepayId = prePayModel.getPrepayid();
                req.nonceStr = prePayModel.getNoncestr();
                req.timeStamp = (System.currentTimeMillis() / 1000) + "";
                req.packageValue = "Sign=WXPay";

                String str = "appid=" + Constants.APP_ID + "&noncestr=" + req.nonceStr + "&package=" + req.packageValue + "&partnerid=" + Constants.MCH_ID + "&prepayid=" + req.prepayId + "&timestamp=" + req.timeStamp + "&key=oJC5nGxuom2e1vJL03EQcH7CloxewnRP";
                req.sign = MD5.getMessageDigest(str.toString().getBytes()).toUpperCase();
                //  KLog.d("wxpay str " + str);
                //  KLog.d("wxpay sign " + req.sign);
                msgApi.registerApp(Constants.APP_ID);
                dismissProgressDialog();
                msgApi.sendReq(req);
            }
        });
    }

    public String genNonceStr() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 10);
        return key;
    }

    //商品信息,用于提交生成预付订单
    private String genProductArgs() {
        String nons = genNonceStr();
        KLog.d("price origin =" + String.format("%.2f", Double.valueOf(price)));

        int p = (int) ((Double.valueOf(String.format("%.2f", Double.valueOf(price)))) * 100);
        String stringA = "appid=" + Constants.APP_ID + "&body=" + osn + "&mch_id=" + Constants.MCH_ID + "&nonce_str=" + nons + "&notify_url=" + Constants.WxpayNotifyURL + "&out_trade_no=" + oid + "A" + nons + "&spbill_create_ip=127.0.0.1" + "&total_fee=" + p + "&trade_type=APP&key=oJC5nGxuom2e1vJL03EQcH7CloxewnRP";

        String sign = MD5.getMessageDigest(stringA.toString().getBytes()).toUpperCase();

        String prepay = "<xml>" +
                "<appid>" + Constants.APP_ID + "</appid>" +
                "<mch_id>" + Constants.MCH_ID + "</mch_id>" +
                "<nonce_str>" + nons + "</nonce_str>" +
                "<sign>" + sign + "</sign>" +
                "<body>" + osn + "</body>" +
                "<out_trade_no>" + oid + "A" + nons + "</out_trade_no>" +
                "<total_fee>" + p + "</total_fee>" +
                "<spbill_create_ip>127.0.0.1</spbill_create_ip>" +
                "<notify_url>" + Constants.WxpayNotifyURL + "</notify_url>" +
                "<trade_type>APP</trade_type>" +
                "</xml>";

        // KLog.d("生成预付提交xml " + prepay);
        return prepay;
    }

    public void pasexml(String xml) {

        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(new StringReader(xml));
            //获取事件类型
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    //文档开始
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tagName = parser.getName();
                        if ("return_code".equals(tagName)) {
                            String code = parser.nextText();
                            if ("SUCCESS".equals(code)) {
                                prePayModel.setSuccess(true);
                            } else {
                                prePayModel.setSuccess(false);
                            }
                            //  KLog.d("return_code" + parser.nextText());
                        }
                        if ("return_msg".equals(tagName)) {
                            //  KLog.d("return_msg" + parser.nextText());
                        }
                        if ("prepay_id".equals(tagName)) {
                            prePayModel.setPrepayid(parser.nextText());
                            //   KLog.d("prepay_id" + parser.nextText());
                        }
                        if ("sign".equals(tagName)) {
                            prePayModel.setSign(parser.nextText());
                            //   KLog.d("prepay_id" + parser.nextText());
                        }
                        if ("nonce_str".equals(tagName)) {
                            prePayModel.setNoncestr(parser.nextText());
                            //   KLog.d("prepay_id" + parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:

                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
        builder.setMessage("下单后24小时订单将被取消, 请尽快完成支付");

        builder.setTitle("确认要离开收银台？");
        builder.setCancelable(false);
        builder.setPositiveButton("继续支付", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("确认离开", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();
    }
}
