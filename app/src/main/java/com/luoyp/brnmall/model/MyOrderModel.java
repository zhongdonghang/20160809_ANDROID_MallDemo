package com.luoyp.brnmall.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyp3314@gmail.com on 16/5/9.
 */
public class MyOrderModel {

    private List<Goods> goodsList = new ArrayList<>();
    private String oid = "";
    private String uid = "";
    private String orderstate = "";
    private String orderamount = "";
    private String addtime = "";
    private String storeid = "";
    private String payfriendname = "";
    private String storename = "";
    private String osn = "";
    private String realpay = "";

    public String getRealpay() {
        return realpay;
    }

    public void setRealpay(String realpay) {
        this.realpay = realpay;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(String orderstate) {
        this.orderstate = orderstate;
    }

    public String getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(String orderamount) {
        this.orderamount = orderamount;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getPayfriendname() {
        return payfriendname;
    }

    public void setPayfriendname(String payfriendname) {
        this.payfriendname = payfriendname;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getOsn() {
        return osn;
    }

    public void setOsn(String osn) {
        this.osn = osn;
    }


}
