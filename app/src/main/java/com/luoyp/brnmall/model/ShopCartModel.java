package com.luoyp.brnmall.model;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MnZi on 2016/5/5.
 */
public class ShopCartModel implements Serializable {

    /**
     * TotalCount : 2
     * ProductAmount : 0.39
     * FullCut : 0
     * OrderAmount : 0.39
     */

    private int TotalCount;
    private double ProductAmount;
    private int FullCut;
    private double OrderAmount;

    private List<CartGoodsBean> CartGoodsBeanList;
    private CartGoodsBean CartGoods;

    public List<CartGoodsBean> getCartGoodsBeanList() {
        return CartGoodsBeanList;
    }

    public void setCartGoodsBeanList(List<CartGoodsBean> cartGoodsBeanList) {
        CartGoodsBeanList = cartGoodsBeanList;
    }

    public CartGoodsBean getCartGoods() {
        return CartGoods;
    }

    public void setCartGoods(CartGoodsBean cartGoods) {
        CartGoods = cartGoods;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public double getProductAmount() {
        return ProductAmount;
    }

    public void setProductAmount(double ProductAmount) {
        this.ProductAmount = ProductAmount;
    }

    public int getFullCut() {
        return FullCut;
    }

    public void setFullCut(int FullCut) {
        this.FullCut = FullCut;
    }

    public double getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(double OrderAmount) {
        this.OrderAmount = OrderAmount;
    }

    public static class CartGoodsBean {

        /**
         * RecordId : 285
         * Oid : 0
         * Uid : 29
         * Sid :
         * Pid : 88
         * PSN : 35545
         * CateId : 85
         * BrandId : 32
         * StoreId : 2
         * StoreCid : 1
         * StoreSTid : 3
         * Name : 碧生源牌减肥茶 2.5g/袋*15袋/盒*4盒
         * ShowImg : ps_1603280928582176397.png
         * DiscountPrice : 0.37
         * ShopPrice : 0.37
         * CostPrice : 0.5
         * MarketPrice : 0.4
         * Weight : 0
         * IsReview : 0
         * RealCount : 1
         * BuyCount : 1
         * SendCount : 0
         * Type : 0
         * PayCredits : 0
         * CouponTypeId : 0
         * ExtCode1 : 0
         * ExtCode2 : 0
         * ExtCode3 : 0
         * ExtCode4 : 0
         * ExtCode5 : 0
         * AddTime : /Date(1462409575000+0800)/
         */

        private int RecordId;
        private int Oid;
        private int Uid;
        private String Sid;
        private int Pid;
        private String PSN;
        private int CateId;
        private int BrandId;
        private int StoreId;
        private int StoreCid;
        private int StoreSTid;
        private String Name;
        private String ShowImg;
        private double DiscountPrice;
        private double ShopPrice;
        private double CostPrice;
        private double MarketPrice;
        private int Weight;
        private int IsReview;
        private int RealCount;
        private int BuyCount;
        private int SendCount;
        private int Type;
        private int PayCredits;
        private int CouponTypeId;
        private int ExtCode1;
        private int ExtCode2;
        private int ExtCode3;
        private int ExtCode4;
        private int ExtCode5;
        private String AddTime;

        public static List<CartGoodsBean> jsonStrToList(String str) throws JSONException {
            List<CartGoodsBean> list = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(str);
            if (jsonArray.length() == 0) {
                return list;
            } else {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject itemObject = jsonArray.getJSONObject(i);
                    JSONArray selectArray = itemObject.getJSONArray("SelectedOrderProductList");
                    for (int j = 0; j < selectArray.length(); j++) {
                        CartGoodsBean goodsBean = new Gson().fromJson(selectArray.getJSONObject(j).toString()
                                , CartGoodsBean.class);
                        list.add(goodsBean);
                    }

                }
                return list;
            }
        }

        public int getRecordId() {
            return RecordId;
        }

        public void setRecordId(int RecordId) {
            this.RecordId = RecordId;
        }

        public int getOid() {
            return Oid;
        }

        public void setOid(int Oid) {
            this.Oid = Oid;
        }

        public int getUid() {
            return Uid;
        }

        public void setUid(int Uid) {
            this.Uid = Uid;
        }

        public String getSid() {
            return Sid;
        }

        public void setSid(String Sid) {
            this.Sid = Sid;
        }

        public int getPid() {
            return Pid;
        }

        public void setPid(int Pid) {
            this.Pid = Pid;
        }

        public String getPSN() {
            return PSN;
        }

        public void setPSN(String PSN) {
            this.PSN = PSN;
        }

        public int getCateId() {
            return CateId;
        }

        public void setCateId(int CateId) {
            this.CateId = CateId;
        }

        public int getBrandId() {
            return BrandId;
        }

        public void setBrandId(int BrandId) {
            this.BrandId = BrandId;
        }

        public int getStoreId() {
            return StoreId;
        }

        public void setStoreId(int StoreId) {
            this.StoreId = StoreId;
        }

        public int getStoreCid() {
            return StoreCid;
        }

        public void setStoreCid(int StoreCid) {
            this.StoreCid = StoreCid;
        }

        public int getStoreSTid() {
            return StoreSTid;
        }

        public void setStoreSTid(int StoreSTid) {
            this.StoreSTid = StoreSTid;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getShowImg() {
            return ShowImg;
        }

        public void setShowImg(String ShowImg) {
            this.ShowImg = ShowImg;
        }

        public double getDiscountPrice() {
            return DiscountPrice;
        }

        public void setDiscountPrice(double DiscountPrice) {
            this.DiscountPrice = DiscountPrice;
        }

        public double getShopPrice() {
            return ShopPrice;
        }

        public void setShopPrice(double ShopPrice) {
            this.ShopPrice = ShopPrice;
        }

        public double getCostPrice() {
            return CostPrice;
        }

        public void setCostPrice(double CostPrice) {
            this.CostPrice = CostPrice;
        }

        public double getMarketPrice() {
            return MarketPrice;
        }

        public void setMarketPrice(double MarketPrice) {
            this.MarketPrice = MarketPrice;
        }

        public int getWeight() {
            return Weight;
        }

        public void setWeight(int Weight) {
            this.Weight = Weight;
        }

        public int getIsReview() {
            return IsReview;
        }

        public void setIsReview(int IsReview) {
            this.IsReview = IsReview;
        }

        public int getRealCount() {
            return RealCount;
        }

        public void setRealCount(int RealCount) {
            this.RealCount = RealCount;
        }

        public int getBuyCount() {
            return BuyCount;
        }

        public void setBuyCount(int BuyCount) {
            this.BuyCount = BuyCount;
        }

        public int getSendCount() {
            return SendCount;
        }

        public void setSendCount(int SendCount) {
            this.SendCount = SendCount;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public int getPayCredits() {
            return PayCredits;
        }

        public void setPayCredits(int PayCredits) {
            this.PayCredits = PayCredits;
        }

        public int getCouponTypeId() {
            return CouponTypeId;
        }

        public void setCouponTypeId(int CouponTypeId) {
            this.CouponTypeId = CouponTypeId;
        }

        public int getExtCode1() {
            return ExtCode1;
        }

        public void setExtCode1(int ExtCode1) {
            this.ExtCode1 = ExtCode1;
        }

        public int getExtCode2() {
            return ExtCode2;
        }

        public void setExtCode2(int ExtCode2) {
            this.ExtCode2 = ExtCode2;
        }

        public int getExtCode3() {
            return ExtCode3;
        }

        public void setExtCode3(int ExtCode3) {
            this.ExtCode3 = ExtCode3;
        }

        public int getExtCode4() {
            return ExtCode4;
        }

        public void setExtCode4(int ExtCode4) {
            this.ExtCode4 = ExtCode4;
        }

        public int getExtCode5() {
            return ExtCode5;
        }

        public void setExtCode5(int ExtCode5) {
            this.ExtCode5 = ExtCode5;
        }

        public String getAddTime() {
            return AddTime;
        }

        public void setAddTime(String AddTime) {
            this.AddTime = AddTime;
        }
    }

}
