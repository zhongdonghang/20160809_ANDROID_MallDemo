package com.luoyp.brnmall.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MnZi on 2016/7/22.
 */
public class GoodsReview {
    /**
     * NickName : nicheng
     * ReviewTime : 2016-07-07 16:17:21
     * Content : 44444
     * UserImg : ua_1606221609371686013.png
     * Star : 5
     * ReplyTime : 2016-07-22 09:55:55
     * ReplyContent :
     */

    private String NickName;
    private String ReviewTime;
    private String Content;
    private String UserImg;
    private int Star;
    private String ReplyTime;
    private String ReplyContent;

    public static List<GoodsReview> arrayGoodsReviewFromData(String str) {

        Type listType = new TypeToken<ArrayList<GoodsReview>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getReviewTime() {
        return ReviewTime;
    }

    public void setReviewTime(String ReviewTime) {
        this.ReviewTime = ReviewTime;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getUserImg() {
        return UserImg;
    }

    public void setUserImg(String UserImg) {
        this.UserImg = UserImg;
    }

    public int getStar() {
        return Star;
    }

    public void setStar(int Star) {
        this.Star = Star;
    }

    public String getReplyTime() {
        return ReplyTime;
    }

    public void setReplyTime(String ReplyTime) {
        this.ReplyTime = ReplyTime;
    }

    public String getReplyContent() {
        return ReplyContent;
    }

    public void setReplyContent(String ReplyContent) {
        this.ReplyContent = ReplyContent;
    }
}
