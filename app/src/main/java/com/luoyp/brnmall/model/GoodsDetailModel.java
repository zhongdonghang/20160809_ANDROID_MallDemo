package com.luoyp.brnmall.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情
 * Created by MnZi on 2016/5/9.
 */
public class GoodsDetailModel {

    private StoreBean StoreInfo; // 店铺信息
    private StoreRankBean StoreRankInfo; // 店铺等级
    private GoodsBean GoodsInfo; // 商品
    private BrandBean BrandInfo; // 品牌
    private List<ImageBean> ImageBeanList; // 商品图文

    public static GoodsDetailModel jsonToGoodsDetailModel(String str) throws JSONException {
        GoodsDetailModel model = new GoodsDetailModel();
        JSONObject dataObject = new JSONObject(str);
        model.setGoodsInfo(new Gson().fromJson(dataObject.getString("ProductInfo")
                ,GoodsDetailModel.GoodsBean.class));
        model.setBrandInfo(new Gson().fromJson(dataObject.getString("BrandInfo")
                ,GoodsDetailModel.BrandBean.class));
//        Type listType = new TypeToken<ArrayList<ImageBean>>(){
//
//        }.getType();
//        List<ImageBean> list = new Gson().fromJson(dataObject.getString("ProductImageList"),listType);
//        model.setImageBeanList(list);
        return model;
    }

    public static List<ImageBean> jsonToImageBeanList(String str) throws JSONException {
        Type listType = new TypeToken<ArrayList<ImageBean>>(){

        }.getType();
        return new Gson().fromJson(str,listType);
    }

    public StoreBean getStoreInfo() {
        return StoreInfo;
    }

    public void setStoreInfo(StoreBean storeInfo) {
        StoreInfo = storeInfo;
    }

    public StoreRankBean getStoreRankInfo() {
        return StoreRankInfo;
    }

    public void setStoreRankInfo(StoreRankBean storeRankInfo) {
        StoreRankInfo = storeRankInfo;
    }

    public GoodsBean getGoodsInfo() {
        return GoodsInfo;
    }

    public void setGoodsInfo(GoodsBean goodsInfo) {
        GoodsInfo = goodsInfo;
    }

    public BrandBean getBrandInfo() {
        return BrandInfo;
    }

    public void setBrandInfo(BrandBean brandInfo) {
        BrandInfo = brandInfo;
    }

    public List<ImageBean> getImageBeanList() {
        return ImageBeanList;
    }

    public void setImageBeanList(List<ImageBean> imageBeanList) {
        ImageBeanList = imageBeanList;
    }

    // 店铺信息
    public static class StoreBean{

        /**
         * StoreId : 2
         * State : 0
         * Name : 精生源商铺11
         * RegionId : 411
         * StoreRid : 1
         * StoreIid : 3
         * Logo :
         * CreateTime : /Date(1408791002000+0800)/
         * Mobile : 15556568157
         * Phone :
         * QQ :
         * WW :
         * DePoint : 10
         * SePoint : 10
         * ShPoint : 10
         * Honesties : 0
         * StateEndTime : /Date(1551283200000+0800)/
         * Theme : default
         * Banner :
         * Announcement :
         * Description :
         */

        private int StoreId;
        private int State;
        private String Name;
        private int RegionId;
        private int StoreRid;
        private int StoreIid;
        private String Logo;
        private String CreateTime;
        private String Mobile;
        private String Phone;
        private String QQ;
        private String WW;
        private int DePoint;
        private int SePoint;
        private int ShPoint;
        private int Honesties;
        private String StateEndTime;
        private String Theme;
        private String Banner;
        private String Announcement;
        private String Description;

        public int getStoreId() {
            return StoreId;
        }

        public void setStoreId(int StoreId) {
            this.StoreId = StoreId;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getRegionId() {
            return RegionId;
        }

        public void setRegionId(int RegionId) {
            this.RegionId = RegionId;
        }

        public int getStoreRid() {
            return StoreRid;
        }

        public void setStoreRid(int StoreRid) {
            this.StoreRid = StoreRid;
        }

        public int getStoreIid() {
            return StoreIid;
        }

        public void setStoreIid(int StoreIid) {
            this.StoreIid = StoreIid;
        }

        public String getLogo() {
            return Logo;
        }

        public void setLogo(String Logo) {
            this.Logo = Logo;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
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

        public String getQQ() {
            return QQ;
        }

        public void setQQ(String QQ) {
            this.QQ = QQ;
        }

        public String getWW() {
            return WW;
        }

        public void setWW(String WW) {
            this.WW = WW;
        }

        public int getDePoint() {
            return DePoint;
        }

        public void setDePoint(int DePoint) {
            this.DePoint = DePoint;
        }

        public int getSePoint() {
            return SePoint;
        }

        public void setSePoint(int SePoint) {
            this.SePoint = SePoint;
        }

        public int getShPoint() {
            return ShPoint;
        }

        public void setShPoint(int ShPoint) {
            this.ShPoint = ShPoint;
        }

        public int getHonesties() {
            return Honesties;
        }

        public void setHonesties(int Honesties) {
            this.Honesties = Honesties;
        }

        public String getStateEndTime() {
            return StateEndTime;
        }

        public void setStateEndTime(String StateEndTime) {
            this.StateEndTime = StateEndTime;
        }

        public String getTheme() {
            return Theme;
        }

        public void setTheme(String Theme) {
            this.Theme = Theme;
        }

        public String getBanner() {
            return Banner;
        }

        public void setBanner(String Banner) {
            this.Banner = Banner;
        }

        public String getAnnouncement() {
            return Announcement;
        }

        public void setAnnouncement(String Announcement) {
            this.Announcement = Announcement;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }
    }

    // 店铺等级
    public static class StoreRankBean{

        /**
         * StoreRid : 1
         * Title : 铁钻店铺
         * Avatar : sra_1409022223128677013.jpg
         * HonestiesLower : 0
         * HonestiesUpper : 500
         * ProductCount : 60
         */

        private int StoreRid;
        private String Title;
        private String Avatar;
        private int HonestiesLower;
        private int HonestiesUpper;
        private int ProductCount;

        public int getStoreRid() {
            return StoreRid;
        }

        public void setStoreRid(int StoreRid) {
            this.StoreRid = StoreRid;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getAvatar() {
            return Avatar;
        }

        public void setAvatar(String Avatar) {
            this.Avatar = Avatar;
        }

        public int getHonestiesLower() {
            return HonestiesLower;
        }

        public void setHonestiesLower(int HonestiesLower) {
            this.HonestiesLower = HonestiesLower;
        }

        public int getHonestiesUpper() {
            return HonestiesUpper;
        }

        public void setHonestiesUpper(int HonestiesUpper) {
            this.HonestiesUpper = HonestiesUpper;
        }

        public int getProductCount() {
            return ProductCount;
        }

        public void setProductCount(int ProductCount) {
            this.ProductCount = ProductCount;
        }
    }

    // 商品信息
    public static class GoodsBean{

        /**
         * Description :
         * Pid : 85
         * PSN : jjyg00215
         * CateId : 81
         * BrandId : 33
         * StoreId : 2
         * StoreCid : 3
         * StoreSTid : 3
         * SKUGid : 0
         * Name : 善元堂 爽之宁片800mg/片*60（片）
         * ShopPrice : 0.41
         * MarketPrice : 0.94
         * CostPrice : 1
         * State : 0
         * IsBest : 0
         * IsHot : 1
         * IsNew : 0
         * DisplayOrder : 0
         * Weight : 60
         * ShowImg : ps_1603251755507933340.png
         * SaleCount : 0
         * VisitCount : 31
         * ReviewCount : 0
         * Star1 : 0
         * Star2 : 0
         * Star3 : 0
         * Star4 : 0
         * Star5 : 0
         * AddTime : /Date(1458899708000+0800)/
         */

        private String Description;
        private int Pid;
        private String PSN;
        private int CateId;
        private int BrandId;
        private int StoreId;
        private int StoreCid;
        private int StoreSTid;
        private int SKUGid;
        private String Name;
        private double ShopPrice;
        private double MarketPrice;
        private double VipPrice;
        private double CostPrice;
        private int State;
        private int IsBest;
        private int IsHot;
        private int IsNew;
        private int DisplayOrder;
        private int Weight;
        private String ShowImg;
        private int SaleCount;
        private int VisitCount;
        private int ReviewCount;
        private int Star1;
        private int Star2;
        private int Star3;
        private int Star4;
        private int Star5;
        private String AddTime;

        public double getVipPrice() {
            return VipPrice;
        }

        public void setVipPrice(double vipPrice) {
            VipPrice = vipPrice;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
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

        public int getSKUGid() {
            return SKUGid;
        }

        public void setSKUGid(int SKUGid) {
            this.SKUGid = SKUGid;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public double getShopPrice() {
            return ShopPrice;
        }

        public void setShopPrice(double ShopPrice) {
            this.ShopPrice = ShopPrice;
        }

        public double getMarketPrice() {
            return MarketPrice;
        }

        public void setMarketPrice(double MarketPrice) {
            this.MarketPrice = MarketPrice;
        }

        public double getCostPrice() {
            return CostPrice;
        }

        public void setCostPrice(int CostPrice) {
            this.CostPrice = CostPrice;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public int getIsBest() {
            return IsBest;
        }

        public void setIsBest(int IsBest) {
            this.IsBest = IsBest;
        }

        public int getIsHot() {
            return IsHot;
        }

        public void setIsHot(int IsHot) {
            this.IsHot = IsHot;
        }

        public int getIsNew() {
            return IsNew;
        }

        public void setIsNew(int IsNew) {
            this.IsNew = IsNew;
        }

        public int getDisplayOrder() {
            return DisplayOrder;
        }

        public void setDisplayOrder(int DisplayOrder) {
            this.DisplayOrder = DisplayOrder;
        }

        public int getWeight() {
            return Weight;
        }

        public void setWeight(int Weight) {
            this.Weight = Weight;
        }

        public String getShowImg() {
            return ShowImg;
        }

        public void setShowImg(String ShowImg) {
            this.ShowImg = ShowImg;
        }

        public int getSaleCount() {
            return SaleCount;
        }

        public void setSaleCount(int SaleCount) {
            this.SaleCount = SaleCount;
        }

        public int getVisitCount() {
            return VisitCount;
        }

        public void setVisitCount(int VisitCount) {
            this.VisitCount = VisitCount;
        }

        public int getReviewCount() {
            return ReviewCount;
        }

        public void setReviewCount(int ReviewCount) {
            this.ReviewCount = ReviewCount;
        }

        public int getStar1() {
            return Star1;
        }

        public void setStar1(int Star1) {
            this.Star1 = Star1;
        }

        public int getStar2() {
            return Star2;
        }

        public void setStar2(int Star2) {
            this.Star2 = Star2;
        }

        public int getStar3() {
            return Star3;
        }

        public void setStar3(int Star3) {
            this.Star3 = Star3;
        }

        public int getStar4() {
            return Star4;
        }

        public void setStar4(int Star4) {
            this.Star4 = Star4;
        }

        public int getStar5() {
            return Star5;
        }

        public void setStar5(int Star5) {
            this.Star5 = Star5;
        }

        public String getAddTime() {
            return AddTime;
        }

        public void setAddTime(String AddTime) {
            this.AddTime = AddTime;
        }
    }

    // 商品品牌
    public static class BrandBean {

        /**
         * BrandId : 33
         * DisplayOrder : 0
         * Name : 善元堂
         * Logo : b_1603281022162660544.jpg
         */

        private int BrandId;
        private int DisplayOrder;
        private String Name;
        private String Logo;

        public int getBrandId() {
            return BrandId;
        }

        public void setBrandId(int BrandId) {
            this.BrandId = BrandId;
        }

        public int getDisplayOrder() {
            return DisplayOrder;
        }

        public void setDisplayOrder(int DisplayOrder) {
            this.DisplayOrder = DisplayOrder;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getLogo() {
            return Logo;
        }

        public void setLogo(String Logo) {
            this.Logo = Logo;
        }
    }

    //商品图片
    public static class ImageBean {

        /**
         * PImgId : 122
         * Pid : 85
         * ShowImg : ps_1603251755507933340.png
         * IsMain : 1
         * DisplayOrder : 0
         * StoreId : 2
         */

        private int PImgId;
        private int Pid;
        private String ShowImg;
        private int IsMain;
        private int DisplayOrder;
        private int StoreId;

        public int getPImgId() {
            return PImgId;
        }

        public void setPImgId(int PImgId) {
            this.PImgId = PImgId;
        }

        public int getPid() {
            return Pid;
        }

        public void setPid(int Pid) {
            this.Pid = Pid;
        }

        public String getShowImg() {
            return ShowImg;
        }

        public void setShowImg(String ShowImg) {
            this.ShowImg = ShowImg;
        }

        public int getIsMain() {
            return IsMain;
        }

        public void setIsMain(int IsMain) {
            this.IsMain = IsMain;
        }

        public int getDisplayOrder() {
            return DisplayOrder;
        }

        public void setDisplayOrder(int DisplayOrder) {
            this.DisplayOrder = DisplayOrder;
        }

        public int getStoreId() {
            return StoreId;
        }

        public void setStoreId(int StoreId) {
            this.StoreId = StoreId;
        }
    }

}
