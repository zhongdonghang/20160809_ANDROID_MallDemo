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

    private GoodsBean GoodsInfo;
    private BrandBean BrandInfo;
    private List<ImageBean> ImageBeanList;

    public static GoodsDetailModel jsonToGoodsDetailModel(String str) throws JSONException {
        GoodsDetailModel model = new GoodsDetailModel();
        JSONObject dataObject = new JSONObject(str);
        model.setGoodsInfo(new Gson().fromJson(dataObject.getString("ProductInfo")
                ,GoodsDetailModel.GoodsBean.class));
        model.setBrandInfo(new Gson().fromJson(dataObject.getString("BrandInfo")
                ,GoodsDetailModel.BrandBean.class));
        Type listType = new TypeToken<ArrayList<ImageBean>>(){

        }.getType();
        List<ImageBean> list = new Gson().fromJson(dataObject.getString("ProductImageList"),listType);
        model.setImageBeanList(list);
        return model;
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
