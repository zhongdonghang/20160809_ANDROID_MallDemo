package com.luoyp.brnmall.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MnZi on 2016/5/12.
 */
public class OrderDetailModel {

    private OrderBean OrderInfo;
    private RegionBean RegionInfo;
    private List<OrderGoodsBean> OrderGoodsList;
    private List<OrderActionBean> OrderActionList;

    public static List<OrderGoodsBean> jsonToOrderGoodsList(String str) throws JSONException {
        Type listType = new TypeToken<ArrayList<OrderGoodsBean>>(){

        }.getType();
        return new Gson().fromJson(str,listType);
    }

    public static List<OrderActionBean> jsonToOrderActionList(String str) throws JSONException {
        Type listType = new TypeToken<ArrayList<OrderActionBean>>(){

        }.getType();
        return new Gson().fromJson(str,listType);
    }

    public OrderBean getOrderInfo() {
        return OrderInfo;
    }

    public void setOrderInfo(OrderBean orderInfo) {
        OrderInfo = orderInfo;
    }

    public RegionBean getRegionInfo() {
        return RegionInfo;
    }

    public void setRegionInfo(RegionBean regionInfo) {
        RegionInfo = regionInfo;
    }

    public List<OrderGoodsBean> getOrderGoodsList() {
        return OrderGoodsList;
    }

    public void setOrdrGoodsList(List<OrderGoodsBean> orderGoodsList) {
        OrderGoodsList = orderGoodsList;
    }

    public List<OrderActionBean> getOrderActionList() {
        return OrderActionList;
    }

    public void setOrderActionList(List<OrderActionBean> orderActionList) {
        OrderActionList = orderActionList;
    }

    // 订单信息
    public static class OrderBean {

        /**
         * Oid : 219
         * OSN : 201605101700452292244
         * Uid : 29
         * OrderState : 200
         * ProductAmount : 1.46
         * OrderAmount : 71.46
         * SurplusMoney : 71.46
         * ParentId : 0
         * IsReview : 0
         * AddTime : /Date(1462870845000+0800)/
         * StoreId : 2
         * StoreName : 精生源商铺11
         * ShipSN :
         * ShipCoId : 0
         * ShipCoName :
         * ShipTime : /Date(-2209017600000+0800)/
         * PaySN :
         * PaySystemName : alipay
         * PayFriendName : 支付宝
         * PayMode : 1
         * PayTime : /Date(-2209017600000+0800)/
         * RegionId : 2244
         * Consignee : 老大
         * Mobile : 18377102233
         * Phone :
         * Email :
         * ZipCode :
         * Address : 路上等
         * BestTime : /Date(-28800000+0800)/
         * ShipFee : 70
         * PayFee : 0
         * FullCut : 0
         * Discount : 0
         * PayCreditCount : 0
         * PayCreditMoney : 0
         * CouponMoney : 0
         * Weight : 0
         * BuyerRemark :
         * IP : 113.16.145.105
         */

        private String Oid;
        private String OSN;
        private String Uid;
        private String OrderState;
        private String ProductAmount;
        private String OrderAmount;
        private String SurplusMoney;
        private String ParentId;
        private String IsReview;
        private String AddTime;
        private String StoreId;
        private String StoreName;
        private String ShipSN;
        private String ShipCoId;
        private String ShipCoName;
        private String ShipTime;
        private String PaySN;
        private String PaySystemName;
        private String PayFriendName;
        private String PayMode;
        private String PayTime;
        private String RegionId;
        private String Consignee;
        private String Mobile;
        private String Phone;
        private String Email;
        private String ZipCode;
        private String Address;
        private String BestTime;
        private String ShipFee;
        private String PayFee;
        private String FullCut;
        private String Discount;
        private String PayCreditCount;
        private String PayCreditMoney;
        private String CouponMoney;
        private String Weight;
        private String BuyerRemark;
        private String IP;

        public String getOid() {
            return Oid;
        }

        public void setOid(String Oid) {
            this.Oid = Oid;
        }

        public String getOSN() {
            return OSN;
        }

        public void setOSN(String OSN) {
            this.OSN = OSN;
        }

        public String getUid() {
            return Uid;
        }

        public void setUid(String Uid) {
            this.Uid = Uid;
        }

        public String getOrderState() {
            return OrderState;
        }

        public void setOrderState(String OrderState) {
            this.OrderState = OrderState;
        }

        public String getProductAmount() {
            return ProductAmount;
        }

        public void setProductAmount(String ProductAmount) {
            this.ProductAmount = ProductAmount;
        }

        public String getOrderAmount() {
            return OrderAmount;
        }

        public void setOrderAmount(String OrderAmount) {
            this.OrderAmount = OrderAmount;
        }

        public String getSurplusMoney() {
            return SurplusMoney;
        }

        public void setSurplusMoney(String SurplusMoney) {
            this.SurplusMoney = SurplusMoney;
        }

        public String getParentId() {
            return ParentId;
        }

        public void setParentId(String ParentId) {
            this.ParentId = ParentId;
        }

        public String getIsReview() {
            return IsReview;
        }

        public void setIsReview(String IsReview) {
            this.IsReview = IsReview;
        }

        public String getAddTime() {
            return AddTime;
        }

        public void setAddTime(String AddTime) {
            this.AddTime = AddTime;
        }

        public String getStoreId() {
            return StoreId;
        }

        public void setStoreId(String StoreId) {
            this.StoreId = StoreId;
        }

        public String getStoreName() {
            return StoreName;
        }

        public void setStoreName(String StoreName) {
            this.StoreName = StoreName;
        }

        public String getShipSN() {
            return ShipSN;
        }

        public void setShipSN(String ShipSN) {
            this.ShipSN = ShipSN;
        }

        public String getShipCoId() {
            return ShipCoId;
        }

        public void setShipCoId(String ShipCoId) {
            this.ShipCoId = ShipCoId;
        }

        public String getShipCoName() {
            return ShipCoName;
        }

        public void setShipCoName(String ShipCoName) {
            this.ShipCoName = ShipCoName;
        }

        public String getShipTime() {
            return ShipTime;
        }

        public void setShipTime(String ShipTime) {
            this.ShipTime = ShipTime;
        }

        public String getPaySN() {
            return PaySN;
        }

        public void setPaySN(String PaySN) {
            this.PaySN = PaySN;
        }

        public String getPaySystemName() {
            return PaySystemName;
        }

        public void setPaySystemName(String PaySystemName) {
            this.PaySystemName = PaySystemName;
        }

        public String getPayFriendName() {
            return PayFriendName;
        }

        public void setPayFriendName(String PayFriendName) {
            this.PayFriendName = PayFriendName;
        }

        public String getPayMode() {
            return PayMode;
        }

        public void setPayMode(String PayMode) {
            this.PayMode = PayMode;
        }

        public String getPayTime() {
            return PayTime;
        }

        public void setPayTime(String PayTime) {
            this.PayTime = PayTime;
        }

        public String getRegionId() {
            return RegionId;
        }

        public void setRegionId(String RegionId) {
            this.RegionId = RegionId;
        }

        public String getConsignee() {
            return Consignee;
        }

        public void setConsignee(String Consignee) {
            this.Consignee = Consignee;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public String getZipCode() {
            return ZipCode;
        }

        public void setZipCode(String ZipCode) {
            this.ZipCode = ZipCode;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getBestTime() {
            return BestTime;
        }

        public void setBestTime(String BestTime) {
            this.BestTime = BestTime;
        }

        public String getShipFee() {
            return ShipFee;
        }

        public void setShipFee(String ShipFee) {
            this.ShipFee = ShipFee;
        }

        public String getPayFee() {
            return PayFee;
        }

        public void setPayFee(String PayFee) {
            this.PayFee = PayFee;
        }

        public String getFullCut() {
            return FullCut;
        }

        public void setFullCut(String FullCut) {
            this.FullCut = FullCut;
        }

        public String getDiscount() {
            return Discount;
        }

        public void setDiscount(String Discount) {
            this.Discount = Discount;
        }

        public String getPayCreditCount() {
            return PayCreditCount;
        }

        public void setPayCreditCount(String PayCreditCount) {
            this.PayCreditCount = PayCreditCount;
        }

        public String getPayCreditMoney() {
            return PayCreditMoney;
        }

        public void setPayCreditMoney(String PayCreditMoney) {
            this.PayCreditMoney = PayCreditMoney;
        }

        public String getCouponMoney() {
            return CouponMoney;
        }

        public void setCouponMoney(String CouponMoney) {
            this.CouponMoney = CouponMoney;
        }

        public String getWeight() {
            return Weight;
        }

        public void setWeight(String Weight) {
            this.Weight = Weight;
        }

        public String getBuyerRemark() {
            return BuyerRemark;
        }

        public void setBuyerRemark(String BuyerRemark) {
            this.BuyerRemark = BuyerRemark;
        }

        public String getIP() {
            return IP;
        }

        public void setIP(String IP) {
            this.IP = IP;
        }
    }

    // 地址信息
    public static class RegionBean {

        /**
         * RegionId : 2244
         * Name : 西乡塘区
         * Spell :
         * ShortSpell :
         * DisplayOrder : 0
         * ParentId : 252
         * Layer : 3
         * ProvinceId : 20
         * ProvinceName : 广西壮族自治区
         * CityId : 252
         * CityName : 南宁市
         */

        private String RegionId;
        private String Name;
        private String Spell;
        private String ShortSpell;
        private String DisplayOrder;
        private String ParentId;
        private String Layer;
        private String ProvinceId;
        private String ProvinceName;
        private String CityId;
        private String CityName;

        public String getRegionId() {
            return RegionId;
        }

        public void setRegionId(String RegionId) {
            this.RegionId = RegionId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getSpell() {
            return Spell;
        }

        public void setSpell(String Spell) {
            this.Spell = Spell;
        }

        public String getShortSpell() {
            return ShortSpell;
        }

        public void setShortSpell(String ShortSpell) {
            this.ShortSpell = ShortSpell;
        }

        public String getDisplayOrder() {
            return DisplayOrder;
        }

        public void setDisplayOrder(String DisplayOrder) {
            this.DisplayOrder = DisplayOrder;
        }

        public String getParentId() {
            return ParentId;
        }

        public void setParentId(String ParentId) {
            this.ParentId = ParentId;
        }

        public String getLayer() {
            return Layer;
        }

        public void setLayer(String Layer) {
            this.Layer = Layer;
        }

        public String getProvinceId() {
            return ProvinceId;
        }

        public void setProvinceId(String ProvinceId) {
            this.ProvinceId = ProvinceId;
        }

        public String getProvinceName() {
            return ProvinceName;
        }

        public void setProvinceName(String ProvinceName) {
            this.ProvinceName = ProvinceName;
        }

        public String getCityId() {
            return CityId;
        }

        public void setCityId(String CityId) {
            this.CityId = CityId;
        }

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String CityName) {
            this.CityName = CityName;
        }
    }

    // 订单商品
    public static class OrderGoodsBean {

        /**
         * RecordId : 285
         * Oid : 219
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
         * RealCount : 3
         * BuyCount : 3
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

        private String RecordId;
        private String Oid;
        private String Uid;
        private String Sid;
        private String Pid;
        private String PSN;
        private String CateId;
        private String BrandId;
        private String StoreId;
        private String StoreCid;
        private String StoreSTid;
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

        public String getRecordId() {
            return RecordId;
        }

        public void setRecordId(String RecordId) {
            this.RecordId = RecordId;
        }

        public String getOid() {
            return Oid;
        }

        public void setOid(String Oid) {
            this.Oid = Oid;
        }

        public String getUid() {
            return Uid;
        }

        public void setUid(String Uid) {
            this.Uid = Uid;
        }

        public String getSid() {
            return Sid;
        }

        public void setSid(String Sid) {
            this.Sid = Sid;
        }

        public String getPid() {
            return Pid;
        }

        public void setPid(String Pid) {
            this.Pid = Pid;
        }

        public String getPSN() {
            return PSN;
        }

        public void setPSN(String PSN) {
            this.PSN = PSN;
        }

        public String getCateId() {
            return CateId;
        }

        public void setCateId(String CateId) {
            this.CateId = CateId;
        }

        public String getBrandId() {
            return BrandId;
        }

        public void setBrandId(String BrandId) {
            this.BrandId = BrandId;
        }

        public String getStoreId() {
            return StoreId;
        }

        public void setStoreId(String StoreId) {
            this.StoreId = StoreId;
        }

        public String getStoreCid() {
            return StoreCid;
        }

        public void setStoreCid(String StoreCid) {
            this.StoreCid = StoreCid;
        }

        public String getStoreSTid() {
            return StoreSTid;
        }

        public void setStoreSTid(String StoreSTid) {
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

    // 订单状态
    public static class OrderActionBean {

        /**
         * Aid : 0
         * Oid : 219
         * Uid : 29
         * RealName : 本人
         * ActionType : 0
         * ActionTime : /Date(1462870845000+0800)/
         * ActionDes : 您提交了订单，等待您付款
         */

        private String Aid;
        private String Oid;
        private String Uid;
        private String RealName;
        private String ActionType;
        private String ActionTime;
        private String ActionDes;

        public String getAid() {
            return Aid;
        }

        public void setAid(String Aid) {
            this.Aid = Aid;
        }

        public String getOid() {
            return Oid;
        }

        public void setOid(String Oid) {
            this.Oid = Oid;
        }

        public String getUid() {
            return Uid;
        }

        public void setUid(String Uid) {
            this.Uid = Uid;
        }

        public String getRealName() {
            return RealName;
        }

        public void setRealName(String RealName) {
            this.RealName = RealName;
        }

        public String getActionType() {
            return ActionType;
        }

        public void setActionType(String ActionType) {
            this.ActionType = ActionType;
        }

        public String getActionTime() {
            return ActionTime;
        }

        public void setActionTime(String ActionTime) {
            this.ActionTime = ActionTime;
        }

        public String getActionDes() {
            return ActionDes;
        }

        public void setActionDes(String ActionDes) {
            this.ActionDes = ActionDes;
        }
    }
}
