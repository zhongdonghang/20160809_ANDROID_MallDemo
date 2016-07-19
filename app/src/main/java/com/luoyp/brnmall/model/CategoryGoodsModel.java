package com.luoyp.brnmall.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyp3314@gmail.com on 16/4/14.
 */
public class CategoryGoodsModel {

    private PageInfoBean PageInfo;
    private List<GoodsBean> GoodsBeanList;

    public static PageInfoBean jsonToPageInfoBean(String str){
        return new Gson().fromJson(str,PageInfoBean.class);
    }

    public static List<GoodsBean> jsonToGoodsBeanList(String str) {
        Type listType = new TypeToken<ArrayList<GoodsBean>>(){

        }.getType();
        return new Gson().fromJson(str,listType);
    }

    public PageInfoBean getPageInfo() {
        return PageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        PageInfo = pageInfo;
    }

    public List<GoodsBean> getGoodsBeanList() {
        return GoodsBeanList;
    }

    public void setGoodsBeanList(List<GoodsBean> goodsBeanList) {
        GoodsBeanList = goodsBeanList;
    }

    public static class PageInfoBean{

        /**
         * PageIndex : 0
         * PageNumber : 1
         * PrePageNumber : 1
         * NextPageNumber : 1
         * PageSize : 20
         * TotalCount : 4
         * TotalPages : 1
         * HasPrePage : false
         * HasNextPage : false
         * IsFirstPage : true
         * IsLastPage : true
         */

        private int PageIndex;
        private int PageNumber;
        private int PrePageNumber;
        private int NextPageNumber;
        private int PageSize;
        private int TotalCount;
        private int TotalPages;
        private boolean HasPrePage;
        private boolean HasNextPage;
        private boolean IsFirstPage;
        private boolean IsLastPage;

        public int getPageIndex() {
            return PageIndex;
        }

        public void setPageIndex(int PageIndex) {
            this.PageIndex = PageIndex;
        }

        public int getPageNumber() {
            return PageNumber;
        }

        public void setPageNumber(int PageNumber) {
            this.PageNumber = PageNumber;
        }

        public int getPrePageNumber() {
            return PrePageNumber;
        }

        public void setPrePageNumber(int PrePageNumber) {
            this.PrePageNumber = PrePageNumber;
        }

        public int getNextPageNumber() {
            return NextPageNumber;
        }

        public void setNextPageNumber(int NextPageNumber) {
            this.NextPageNumber = NextPageNumber;
        }

        public int getPageSize() {
            return PageSize;
        }

        public void setPageSize(int PageSize) {
            this.PageSize = PageSize;
        }

        public int getTotalCount() {
            return TotalCount;
        }

        public void setTotalCount(int TotalCount) {
            this.TotalCount = TotalCount;
        }

        public int getTotalPages() {
            return TotalPages;
        }

        public void setTotalPages(int TotalPages) {
            this.TotalPages = TotalPages;
        }

        public boolean isHasPrePage() {
            return HasPrePage;
        }

        public void setHasPrePage(boolean HasPrePage) {
            this.HasPrePage = HasPrePage;
        }

        public boolean isHasNextPage() {
            return HasNextPage;
        }

        public void setHasNextPage(boolean HasNextPage) {
            this.HasNextPage = HasNextPage;
        }

        public boolean isIsFirstPage() {
            return IsFirstPage;
        }

        public void setIsFirstPage(boolean IsFirstPage) {
            this.IsFirstPage = IsFirstPage;
        }

        public boolean isIsLastPage() {
            return IsLastPage;
        }

        public void setIsLastPage(boolean IsLastPage) {
            this.IsLastPage = IsLastPage;
        }
    }
    public static class GoodsBean{

        /**
         * StoreName : 精生源商铺11
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
         * VisitCount : 30
         * ReviewCount : 0
         * Star1 : 0
         * Star2 : 0
         * Star3 : 0
         * Star4 : 0
         * Star5 : 0
         * AddTime : /Date(1458899708000+0800)/
         */

        private String StoreName;
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
        private double VipPrice;
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

        public String getStoreName() {
            return StoreName;
        }

        public void setStoreName(String StoreName) {
            this.StoreName = StoreName;
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
}
