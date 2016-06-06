package com.luoyp.brnmall.model;

/**
 * Created by lyp3314@gmail.com on 16/5/9.
 */
public class HomeGoods {
    private String pid = "";
    private String pname = "";
    private String img = "";
    private String price = "";
    private String markiprice = "";

    public String getMarkiprice() {
        return markiprice;
    }

    public void setMarkiprice(String markiprice) {
        this.markiprice = markiprice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}