package com.luoyp.brnmall.model;

import java.util.List;

/**
 * Created by MnZi on 2016/5/4.
 */
public class OrderModel {

    /**
     * PageIndex : 0
     * PageNumber : 1
     * PrePageNumber : 1
     * NextPageNumber : 1
     * PageSize : 10
     * TotalCount : 2
     * TotalPages : 1
     * HasPrePage : false
     * HasNextPage : false
     * IsFirstPage : true
     * IsLastPage : true
     */

    private PageModelBean PageModel;
    /**
     * OId : 157
     * OSN : 201605041523156292244
     * UId : 29
     * OrderState : 30
     * OrderAmount : 0.02
     * ParentId : 0
     * IsReview : 0
     * AddTime : 2016/5/4 15:23:15
     * StoreId : 6
     * StoreName : 精生缘店铺
     * ShipCoid : 0
     * ShipCoName :
     * PayFriendName : 支付宝
     * PayMode : 1
     * Consignee : 老大
     * ProList : [{"PId":101,"PName":"韩都生命能量水机","ShowImg":"ps_1604111606194493394.jpg"}]
     */

    private List<OrderListBean> OrderList;

    public PageModelBean getPageModel() {
        return PageModel;
    }

    public void setPageModel(PageModelBean PageModel) {
        this.PageModel = PageModel;
    }

    public List<OrderListBean> getOrderList() {
        return OrderList;
    }

    public void setOrderList(List<OrderListBean> OrderList) {
        this.OrderList = OrderList;
    }

    public static class PageModelBean {
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

    public static class OrderListBean {
        private int OId;
        private String OSN;
        private int UId;
        private int OrderState;
        private double OrderAmount;
        private int ParentId;
        private int IsReview;
        private String AddTime;
        private int StoreId;
        private String StoreName;
        private int ShipCoid;
        private String ShipCoName;
        private String PayFriendName;
        private int PayMode;
        private String Consignee;
        /**
         * PId : 101
         * PName : 韩都生命能量水机
         * ShowImg : ps_1604111606194493394.jpg
         */

        private List<ProListBean> ProList;

        public int getOId() {
            return OId;
        }

        public void setOId(int OId) {
            this.OId = OId;
        }

        public String getOSN() {
            return OSN;
        }

        public void setOSN(String OSN) {
            this.OSN = OSN;
        }

        public int getUId() {
            return UId;
        }

        public void setUId(int UId) {
            this.UId = UId;
        }

        public int getOrderState() {
            return OrderState;
        }

        public void setOrderState(int OrderState) {
            this.OrderState = OrderState;
        }

        public double getOrderAmount() {
            return OrderAmount;
        }

        public void setOrderAmount(double OrderAmount) {
            this.OrderAmount = OrderAmount;
        }

        public int getParentId() {
            return ParentId;
        }

        public void setParentId(int ParentId) {
            this.ParentId = ParentId;
        }

        public int getIsReview() {
            return IsReview;
        }

        public void setIsReview(int IsReview) {
            this.IsReview = IsReview;
        }

        public String getAddTime() {
            return AddTime;
        }

        public void setAddTime(String AddTime) {
            this.AddTime = AddTime;
        }

        public int getStoreId() {
            return StoreId;
        }

        public void setStoreId(int StoreId) {
            this.StoreId = StoreId;
        }

        public String getStoreName() {
            return StoreName;
        }

        public void setStoreName(String StoreName) {
            this.StoreName = StoreName;
        }

        public int getShipCoid() {
            return ShipCoid;
        }

        public void setShipCoid(int ShipCoid) {
            this.ShipCoid = ShipCoid;
        }

        public String getShipCoName() {
            return ShipCoName;
        }

        public void setShipCoName(String ShipCoName) {
            this.ShipCoName = ShipCoName;
        }

        public String getPayFriendName() {
            return PayFriendName;
        }

        public void setPayFriendName(String PayFriendName) {
            this.PayFriendName = PayFriendName;
        }

        public int getPayMode() {
            return PayMode;
        }

        public void setPayMode(int PayMode) {
            this.PayMode = PayMode;
        }

        public String getConsignee() {
            return Consignee;
        }

        public void setConsignee(String Consignee) {
            this.Consignee = Consignee;
        }

        public List<ProListBean> getProList() {
            return ProList;
        }

        public void setProList(List<ProListBean> ProList) {
            this.ProList = ProList;
        }

        public static class ProListBean {
            private int PId;
            private String PName;
            private String ShowImg;

            public int getPId() {
                return PId;
            }

            public void setPId(int PId) {
                this.PId = PId;
            }

            public String getPName() {
                return PName;
            }

            public void setPName(String PName) {
                this.PName = PName;
            }

            public String getShowImg() {
                return ShowImg;
            }

            public void setShowImg(String ShowImg) {
                this.ShowImg = ShowImg;
            }
        }
    }
}
