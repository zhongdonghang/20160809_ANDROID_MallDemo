package com.luoyp.brnmall.api;

import com.luoyp.xlibrary.net.OkHttpClientManager;

/**
 * Created by lyp3314@gmail.com on 16/4/14.
 */
public class BrnmallAPI {
    public static String BaseIP = "http://jsy.nnbetter.com";
    public static String BaseURL = BaseIP + ":8027/app.asmx/";
    public static String BaseImgUrl1 = BaseIP + "/upload/store/";
    public static String BaseImgUrl2 = "/product/show/thumb100_100/";
    public static String BaseImgUrl3 = "/product/show/thumb800_800/";

    /**
     * 获取商品目录
     */
    public static void getCategory(String jsonStr, ApiCallback<String> callback) {
        OkHttpClientManager.postAsyn(BaseURL + "GetCategoryLay1", "", callback, "getCategory");
    }

    /**
     * 根据分类Id获取商品列表
     *
     * @param cateId     分类id
     * @param pageNumber 页码
     * @param callback   回调
     */
    public static void getProductListByCateId(String cateId, String pageNumber, ApiCallback<String> callback) {

        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("cateId", cateId)
                , new OkHttpClientManager.Param("pageNumber", pageNumber)
                , new OkHttpClientManager.Param("pageSize", "20")
                , new OkHttpClientManager.Param("brandid", "")
                , new OkHttpClientManager.Param("filterprice", "")
                , new OkHttpClientManager.Param("onlystock", "")
                , new OkHttpClientManager.Param("sortcolumn", "")
                , new OkHttpClientManager.Param("sortdirection", "")};

        OkHttpClientManager.postAsyn(BaseURL + "GetProductListByCateId", params, callback, "getProductListByCateId");
    }

    /**
     * 用户登录
     *
     * @param account  用户名
     * @param pwd      密码
     * @param callback 回调
     */
    public static void doLogin(String account, String pwd, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("accountName", account),
                new OkHttpClientManager.Param("password", pwd), new OkHttpClientManager.Param("returnUrl", "")};
        OkHttpClientManager.postAsyn(BaseURL + "Login", params, callback, "doLogin");
    }

    /**
     * 注册
     *
     * @param account       用户名
     * @param password      密码
     * @param introduceName 介绍人
     * @param callback      回调
     */
    public static void doRegister(String account, String password, String introduceName, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("accountName", account),
                new OkHttpClientManager.Param("password", password), new OkHttpClientManager.Param("confirmPwd", password)
                , new OkHttpClientManager.Param("introduceName", introduceName)};
        OkHttpClientManager.postAsyn(BaseURL + "Register", params, callback, "doRegister");
    }

    /**
     * 购物车
     *
     * @param uid      id
     * @param callback 回调
     */
    public static void getMyCart(String uid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("uid", uid)};
        OkHttpClientManager.postAsyn(BaseURL + "GetMyCart", params, callback, "getMyCart");
    }

    /**
     * 添加商品到购物车
     *
     * @param pid      商品id
     * @param uid      用户id
     * @param count    商品数量
     * @param callback 回调
     */
    public static void addProductToCart(String pid, String uid, String count, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("pid", pid),
                new OkHttpClientManager.Param("uid", uid), new OkHttpClientManager.Param("count", count)};
        OkHttpClientManager.postAsyn(BaseURL + "AddProductToCart", params, callback, "addProductToCart");
    }

    /**
     * 删除购物车中的商品
     *
     * @param pid
     * @param uid
     * @param callback
     */
    public static void deleteCartProduct(String pid, String uid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("pid", pid),
                new OkHttpClientManager.Param("uid", uid)};
        OkHttpClientManager.postAsyn(BaseURL + "DeleteCartProduct", params, callback, "deleteCartProduct");
    }

    /**
     * 我的商品收藏夹
     *
     * @param uid      用户id
     * @param callback 回调
     */
    public static void favoriteProductList(String uid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("uid", uid)};
        OkHttpClientManager.postAsyn(BaseURL + "FavoriteProductList", params, callback, "favoriteProductList");
    }

    /**
     * 添加商品到收藏夹
     *
     * @param pid      商品id
     * @param uid      用户id
     * @param callback 回调
     */
    public static void addProductToFavorite(String pid, String uid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("pid", pid)
                , new OkHttpClientManager.Param("uid", uid)};
        OkHttpClientManager.postAsyn(BaseURL + "AddProductToFavorite", params, callback, "addProductToFavorite");
    }

    /**
     * 删除商品收藏
     *
     * @param pid      商品id
     * @param uid      用户id
     * @param callback 回调
     */
    public static void deleteFavoriteProduct(String pid, String uid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("pid", pid)
                , new OkHttpClientManager.Param("uid", uid)};
        OkHttpClientManager.postAsyn(BaseURL + "DeleteFavoriteProduct", params, callback, "deleteFavoriteProduct");
    }

    /**
     * 我的店铺收藏夹
     *
     * @param uid      用户id
     * @param callback 回调
     */
    public static void favoriteStoreList(String uid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("uid", uid)};
        OkHttpClientManager.postAsyn(BaseURL + "FavoriteStoreList", params, callback, "favoriteStoreList");
    }

    /**
     * 添加店铺到收藏夹
     *
     * @param storeId  店铺id
     * @param uid      用户id
     * @param callback 回调
     */
    public static void addStoreToFavorite(String storeId, String uid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("storeId", storeId)
                , new OkHttpClientManager.Param("uid", uid)};
        OkHttpClientManager.postAsyn(BaseURL + "AddStoreToFavorite", params, callback, "addStoreToFavorite");
    }

    /**
     * 删除店铺收藏
     *
     * @param storeId  店铺id
     * @param uid      用户id
     * @param callback 回调
     */
    public static void deleteFavoriteStore(String storeId, String uid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("storeId", storeId)
                , new OkHttpClientManager.Param("uid", uid)};
        OkHttpClientManager.postAsyn(BaseURL + "DeleteFavoriteStore", params, callback, "deleteFavoriteStore");
    }

    /**
     * 我的订单
     *
     * @param uid        用户id
     * @param pageNumber 页码
     * @param state      状态
     * @param callback   回调
     */
    public static void getMyOrderList(String uid, String pageNumber, String state, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("uid", uid)
                , new OkHttpClientManager.Param("pageNumber", pageNumber)
                , new OkHttpClientManager.Param("pageSize", "20")
                , new OkHttpClientManager.Param("state", state)};
        OkHttpClientManager.postAsyn(BaseURL + "GetMyOrderList", params, callback, "getMyOrderList");
    }

    public static void createOrder(String uid, String said, String orderList, String payName, String remark, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("uid", uid)
                , new OkHttpClientManager.Param("saId", said)
                , new OkHttpClientManager.Param("selectedCartItemKeyList", orderList)
                , new OkHttpClientManager.Param("buyerRemark", remark)
                , new OkHttpClientManager.Param("paycreditcount", "")
                , new OkHttpClientManager.Param("couponidlist", "")
                , new OkHttpClientManager.Param("couponsnlist", "")
                , new OkHttpClientManager.Param("fullcut", "")
                , new OkHttpClientManager.Param("bestTime", "")
                , new OkHttpClientManager.Param("ip", "")
                , new OkHttpClientManager.Param("payName", payName)};

        OkHttpClientManager.postAsyn(BaseURL + "OrderCreate", params, callback, "createOrder");
    }

    /**
     * 商品详情
     *
     * @param pid      商品id
     * @param callback 回调
     */
    public static void getProductDetail(String pid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("pid", pid)};
        OkHttpClientManager.postAsyn(BaseURL + "Product", params, callback, "getProductDetail");
    }

    public static void createWechatPrepay(String xml, ApiCallback<String> callback) {

        OkHttpClientManager.postAsyn("https://api.mch.weixin.qq.com/pay/unifiedorder", xml, callback, "createWechatPrepay");
    }

    /**
     * 取消点单
     *
     * @param oid      订单号
     * @param uid      用户id
     * @param callback 回调
     */
    public static void doCancelOrder(String oid, String uid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("oid", oid),
                new OkHttpClientManager.Param("uid", uid)};
        OkHttpClientManager.postAsyn(BaseURL + "OrderCancel", params, callback, "doCancelOrder");
    }

    /**
     * @param uid
     * @param callback
     */
    public static void getMyAddress(String uid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("uid", uid)};
        OkHttpClientManager.postAsyn(BaseURL + "MyShipAddressList", params, callback, "getMyAddress");
    }

    /**
     * 添加/修改 收货地址
     *
     * @param params   参数
     * @param callback 回调
     */
    public static void addMyAddress(OkHttpClientManager.Param[] params, ApiCallback<String> callback) {
        OkHttpClientManager.postAsyn(BaseURL + "EditShipAddress", params, callback, "addMyAddress");
    }

    /**
     * 删除收货地址
     *
     * @param uid      用户地址
     * @param said     收货地址 id
     * @param callback 回调
     */
    public static void deleteAddress(String uid, String said, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("uid", uid),
                new OkHttpClientManager.Param("said", said)
        };
        OkHttpClientManager.postAsyn(BaseURL + "DeleteShipAddress", params, callback, "deleteAddress");
    }
}
