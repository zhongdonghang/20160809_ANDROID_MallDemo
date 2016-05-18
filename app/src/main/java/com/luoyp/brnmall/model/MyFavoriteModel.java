package com.luoyp.brnmall.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MnZi on 2016/5/18.
 */
public class MyFavoriteModel {
//    private List<GoodsBean> GoodsBeanList;
//    private List<StoreBean> StoreBeanList;

    public static List<GoodsBean> jsonToGoodsBeanList(String str) throws JSONException {
        Type listType = new TypeToken<ArrayList<GoodsBean>>(){

        }.getType();
        return new Gson().fromJson(str,listType);
    }

    public static List<StoreBean> jsonToStoreBeanList(String str) throws JSONException {
        Type listType = new TypeToken<ArrayList<StoreBean>>(){

        }.getType();
        return new Gson().fromJson(str,listType);
    }

    public static class GoodsBean{

        /**
         * recordid : 21
         * uid : 29
         * state : 0
         * addtime : 2016/5/5 8:53:07
         * pid : 88
         * storeid : 2
         * name : 碧生源牌减肥茶 2.5g/袋*15袋/盒*4盒
         * shopprice : 0.37
         * showimg : ps_1603280928582176397.png
         * storename : 精生源商铺11
         */

        private String recordid;
        private String uid;
        private String state;
        private String addtime;
        private String pid;
        private String storeid;
        private String name;
        private String shopprice;
        private String showimg;
        private String storename;

        public String getRecordid() {
            return recordid;
        }

        public void setRecordid(String recordid) {
            this.recordid = recordid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getStoreid() {
            return storeid;
        }

        public void setStoreid(String storeid) {
            this.storeid = storeid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShopprice() {
            return shopprice;
        }

        public void setShopprice(String shopprice) {
            this.shopprice = shopprice;
        }

        public String getShowimg() {
            return showimg;
        }

        public void setShowimg(String showimg) {
            this.showimg = showimg;
        }

        public String getStorename() {
            return storename;
        }

        public void setStorename(String storename) {
            this.storename = storename;
        }
    }

    public static class StoreBean{

        /**
         * recordid : 13
         * uid : 29
         * storeid : 2
         * addtime : 2016/5/5 8:53:03
         * name : 精生源商铺11
         * logo :
         */

        private String recordid;
        private String uid;
        private String storeid;
        private String addtime;
        private String name;
        private String logo;

        public String getRecordid() {
            return recordid;
        }

        public void setRecordid(String recordid) {
            this.recordid = recordid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getStoreid() {
            return storeid;
        }

        public void setStoreid(String storeid) {
            this.storeid = storeid;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}
