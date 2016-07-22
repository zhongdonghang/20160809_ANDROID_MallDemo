package com.luoyp.brnmall.api;

import com.luoyp.xlibrary.net.OkHttpClientManager;

/**
 * Created by lyp3314@gmail.com on 16/4/14.
 */
public class BrnmallAPI {
    //3号服务器：

    public static String BaseIP = "http://jsy.nnbetter.com";
    public static String BaseURL = BaseIP + "/api/app.asmx/";

    //四号服务器：
    //   public static String BaseIP = "http://www.888jsy.com";
    //   public static String BaseURL = BaseIP + "/api/app.asmx/";

    public static String BaseImgUrl1 = BaseIP + "/upload/store/";
    public static String BaseImgUrl2 = "/product/show/thumb100_100/";
    public static String BaseImgUrl3 = "/product/show/thumb800_800/";
    public static String adImgUrl = BaseIP + "/upload/advert/";
    public static String brandImgUrl = BaseIP + "/upload/brand/thumb100_100/";
    public static String userImgUrl = BaseIP + "/upload/user/thumb100_100/";

    /**
     * 获取商品目录
     */
    public static void getCategory(String jsonStr, ApiCallback<String> callback) {
        OkHttpClientManager.postAsyn(BaseURL + "GetCategoryLay1", "", callback, "getCategory");
    }

    public static void getCity(String id, ApiCallback<String> callback) {
        OkHttpClientManager.postAsyn(BaseIP + "/tool/citylist?provinceId=" + id, "", callback, "getCity");
    }

    public static void getDiqu(String id, ApiCallback<String> callback) {
        OkHttpClientManager.postAsyn(BaseIP + "/tool/countylist?cityId=" + id, "", callback, "getDiqu");
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

    public static void userEdit(
            String uid
            , String nickName
            , String realName
            , String gender
            , String idCard
            , String birthDay
            , String regionId
            , String bio
            , String address,
            ApiCallback<String> callback) {

        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("uid", uid)
                , new OkHttpClientManager.Param("nickName", nickName)
                , new OkHttpClientManager.Param("realName", realName)
                , new OkHttpClientManager.Param("gender", gender)
                , new OkHttpClientManager.Param("idCard", idCard)
                , new OkHttpClientManager.Param("birthDay", birthDay)
                , new OkHttpClientManager.Param("regionId", regionId)
                , new OkHttpClientManager.Param("address", address)
                , new OkHttpClientManager.Param("bio", bio)};

        OkHttpClientManager.postAsyn(BaseURL + "UserEdit", params, callback, "UserEdit");
    }

    /**
     * 根据分类Id获取商品列表
     *
     * @param cateId     分类id
     * @param pageNumber 页码
     * @param callback   回调
     */
    public static void getProductListByBrandId(String cateId, String brandId, String pageNumber, ApiCallback<String> callback) {

        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("cateId", cateId)
                , new OkHttpClientManager.Param("pageNumber", pageNumber)
                , new OkHttpClientManager.Param("pageSize", "20")
                , new OkHttpClientManager.Param("brandid", brandId)
                , new OkHttpClientManager.Param("filterprice", "")
                , new OkHttpClientManager.Param("onlystock", "")
                , new OkHttpClientManager.Param("sortcolumn", "")
                , new OkHttpClientManager.Param("sortdirection", "")};

        OkHttpClientManager.postAsyn(BaseURL + "GetProductListByCateId", params, callback, "getProductListByBrandId");
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

    public static void GetPayPluginList(String uid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("uid", uid)};
        OkHttpClientManager.postAsyn(BaseURL + "GetPayPluginList", params, callback, "GetPayPluginList");
    }

    public static void CreditPayOrder(String uid, String oidList, String psw, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("uid", uid), new OkHttpClientManager.Param("oidList", oidList), new OkHttpClientManager.Param("psw", psw)};
        OkHttpClientManager.postAsyn(BaseURL + "CreditPayOrder", params, callback, "CreditPayOrder");
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


    public static void MallSearch(String keyword, String pagenumber, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("keyword", keyword),
                new OkHttpClientManager.Param("uid", ""),
                new OkHttpClientManager.Param("cateid", ""),
                new OkHttpClientManager.Param("brandid", ""),
                new OkHttpClientManager.Param("filterprice", ""),
                new OkHttpClientManager.Param("sortcolumn", ""),
                new OkHttpClientManager.Param("sortdirection", ""),
                new OkHttpClientManager.Param("pagenumber", pagenumber),
                new OkHttpClientManager.Param("pagesize", "500"),
                new OkHttpClientManager.Param("type", "1")};
        OkHttpClientManager.postAsyn(BaseURL + "MallSearch", params, callback, "MallSearch");
    }

    public static void userAvatarEdit(String uid, String avatar, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("uid", uid), new OkHttpClientManager.Param("avatar", avatar)};
        OkHttpClientManager.postAsyn(BaseURL + "UserAvatarEdit", params, callback, "userAvatarEdit");
    }


    public static void ReviewOrder(String oid, String uid, String stars, String messages, String opids, String descriptionStar, String serviceStar, String shipStar, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("oid", oid)
                , new OkHttpClientManager.Param("uid", uid)
                , new OkHttpClientManager.Param("stars", stars)
                , new OkHttpClientManager.Param("messages", messages)
                , new OkHttpClientManager.Param("opids", opids)
                , new OkHttpClientManager.Param("descriptionStar", descriptionStar)
                , new OkHttpClientManager.Param("serviceStar", serviceStar)
                , new OkHttpClientManager.Param("shipStar", shipStar)};
        OkHttpClientManager.postAsyn(BaseURL + "ReviewOrder", params, callback, "ReviewOrder");
    }

    /**
     * 资金日志
     *
     * @param uid
     * @param pageIndex
     * @param callback
     */
    public static void payCreditList(String uid, String pageIndex, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("uid", uid), new OkHttpClientManager.Param("pageSize", "10"), new OkHttpClientManager.Param("pageNumber", pageIndex)};
        OkHttpClientManager.postAsyn(BaseURL + "PayCreditList", params, callback, "PayCreditList");
    }

    /**
     * 提现记录
     *
     * @param uid
     * @param pageIndex
     * @param callback
     */
    public static void withdrawlList(String uid, String pageIndex, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("uid", uid), new OkHttpClientManager.Param("pageSize", "10"), new OkHttpClientManager.Param("pageNumber", pageIndex)};
        OkHttpClientManager.postAsyn(BaseURL + "WithdrawlList", params, callback, "WithdrawlList");
    }

    /**
     * 申请提现
     *
     * @param uid
     * @param applyAmount
     * @param payAccount
     * @param amountRemark
     * @param payType
     * @param phone
     * @param callback
     */

    public static void withdrawlApply(String uid, String applyAmount, String payAccount, String amountRemark, String payType, String phone, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("uid", uid), new OkHttpClientManager.Param("payAccount", payAccount), new OkHttpClientManager.Param("applyAmount", applyAmount), new OkHttpClientManager.Param("amountRemark", amountRemark), new OkHttpClientManager.Param("payType", payType), new OkHttpClientManager.Param("phone", phone)};
        OkHttpClientManager.postAsyn(BaseURL + "WithdrawlApply", params, callback, "WithdrawlApply");
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

    public static void orderReceive(String uid, String oid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("uid", uid),
                new OkHttpClientManager.Param("oid", oid)};
        OkHttpClientManager.postAsyn(BaseURL + "OrderReceive", params, callback, "OrderReceive");
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


    public static void getShipFreeAmount(String uid, String saId, String selectedCartItemKeyList, String shipmode, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("uid", uid)
                , new OkHttpClientManager.Param("saId", saId), new OkHttpClientManager.Param("selectedCartItemKeyList", selectedCartItemKeyList), new OkHttpClientManager.Param("shipmode", shipmode)};
        OkHttpClientManager.postAsyn(BaseURL + "GetShipFreeAmount", params, callback, "GetShipFreeAmount");
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

    public static void getUserInfo(String uid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("uid", uid)};
        OkHttpClientManager.postAsyn(BaseURL + "GetUserById", params, callback, "GetUserById");
    }


    /**
     * 获取关系人
     *
     * @param uid
     * @param callback
     */
    public static void getIntroducers(String uid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {new OkHttpClientManager.Param("uid", uid)};
        OkHttpClientManager.postAsyn(BaseURL + "GetIntroducers", params, callback, "GetIntroducers");
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

    public static void createOrder(String uid, String said, String orderList, String payName, String remark, String shipmode, ApiCallback<String> callback) {
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
                , new OkHttpClientManager.Param("payName", payName)
                , new OkHttpClientManager.Param("shipmode", shipmode)};

        OkHttpClientManager.postAsyn(BaseURL + "OrderCreateNew", params, callback, "OrderCreateNew");
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

    public static void GetAdvertList(String adPosId, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("adPosId", adPosId)};
        OkHttpClientManager.postAsyn(BaseURL + "GetAdvertList", params, callback, "GetAdvertList");
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

    /**
     * 订单详情
     *
     * @param uid      用户id
     * @param oid      订单id
     * @param callback 回调
     */
    public static void getOrderDetail(String uid, String oid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("uid", uid),
                new OkHttpClientManager.Param("oid", oid)
        };
        OkHttpClientManager.postAsyn(BaseURL + "OrderDetail", params, callback, "getOrderDetail");
    }

    public static void GetUserRank(String uid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("uid", uid)
        };
        OkHttpClientManager.postAsyn(BaseURL + "GetUserRank", params, callback, "GetUserRank");
    }

    public static void GetCateBrandList(String cateId, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("cateId", cateId)
        };
        OkHttpClientManager.postAsyn(BaseURL + "GetCateBrandList", params, callback, "GetCateBrandList");
    }

    /**
     * 发送验证码到手机
     *
     * @param phone    手机号
     * @param type     类型
     * @param callback 回调
     */
    public static void sendVerifyCodeToPhone(String phone, String type, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("phoneNumber", phone),
                new OkHttpClientManager.Param("type", type)
        };
        OkHttpClientManager.postAsyn(BaseURL + "SendPhoneVerifyCode", params, callback, "sendVerifyCodeToPhone");
    }

    /**
     * 设置新密码
     *
     * @param phone    手机号
     * @param pwd      新密码
     * @param callback 回调
     */
    public static void resetPassword(String phone, String pwd, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("userName", phone),
                new OkHttpClientManager.Param("password", pwd),
                new OkHttpClientManager.Param("confirmPwd", pwd)
        };
        OkHttpClientManager.postAsyn(BaseURL + "ReSetPassword", params, callback, "resetPassword");
    }

    /**
     * 设置支付密码
     *
     * @param uid      手机号
     * @param pwd      新密码
     * @param callback 回调
     */
    public static void resetPayPwd(String uid, String pwd, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("uid", uid),
                new OkHttpClientManager.Param("password", pwd),
                new OkHttpClientManager.Param("confirmpwd", pwd)
        };
        OkHttpClientManager.postAsyn(BaseURL + "UserPayPasswordEdit", params, callback, "resetPayPwd");
    }

    /**
     * 获取店铺信息
     * @param sid
     * @param callback
     */
    public static void getStoreInfo(String sid,ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("storeId", sid)
        };
        OkHttpClientManager.getAsyn(BaseURL + "GetStoreInfo", params, callback, "getStoreInfo");
    }

    /**
     * 店铺商品搜索
     * @param sid
     * @param callback
     */
    public static void getStoreGoods(String sid, ApiCallback<String> callback) {
        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("storeId", sid),
                new OkHttpClientManager.Param("pageSize", "1000"),
                new OkHttpClientManager.Param("pageNumber", "1"),
                new OkHttpClientManager.Param("storecid", ""),
                new OkHttpClientManager.Param("keyword", ""),
                new OkHttpClientManager.Param("sortcolumn", ""),
                new OkHttpClientManager.Param("sortdirection", "")
        };
        OkHttpClientManager.postAsyn(BaseURL + "StoreProductSearch", params, callback, "getStoreGoods");
    }

}
