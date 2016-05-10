package com.luoyp.brnmall.wxapi;

import com.luoyp.brnmall.api.BrnmallAPI;

public class Constants {


    //appid
    //请同时修改  androidmanifest.xml里面，.PayActivityd里的属性<data android:scheme="wxb4ba3c02aa476ea1"/>为新设置的appid
    public static final String APP_ID = "wxdf532fb158de003e";


    //商户号
    public static final String MCH_ID = "1341572401";
    public static final String WxpayNotifyURL = BrnmallAPI.BaseIP + "/weixin/payresult";

    //  API密钥，在商户平台设置
    public static final String API_KEY = "oJC5nGxuom2e1vJL03EQcH7CloxewnRP";


}
