package com.luoyp.brnmall.api;

import com.luoyp.xlibrary.net.OkHttpClientManager;

/**
 * Created by lyp3314@gmail.com on 16/4/14.
 */
public class BrnmallAPI {
    public static String BaseIP = "http://jsy.nnbetter.com";
    public static String BaseURL = BaseIP + ":8027/app.asmx/";
    public static String BaseImgUrl1 = BaseIP + ":8026/upload/store/";
    public static String BaseImgUrl2 = "/product/show/thumb230_230/";

    /**
     * 获取商品目录
     */
    public static void getCategory(String jsonStr, ApiCallback<String> callback) {
        OkHttpClientManager.postAsyn(BaseURL + "GetCategoryLay1", "", callback, "getCategory");
    }
}
