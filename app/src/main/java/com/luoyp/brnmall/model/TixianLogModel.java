package com.luoyp.brnmall.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyp3314@gmail.com on 16/6/23.
 */

public class TixianLogModel {


    private int RecordId = -1;
    private int Uid = -1;
    private int State = -1;
    private int PayType = -1;
    private String PayAccount = "";
    private double ApplyAmount = -1;
    private String ApplyRemark = "";
    private String ApplyTime = "";
    private String Phone = "";
    private int OperatorUid = -1;
    private String OperatTime = "";
    private String Reason = "";

    public static TixianLogModel objectFromData(String str) {

        return new Gson().fromJson(str, TixianLogModel.class);
    }

    public static TixianLogModel objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), TixianLogModel.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<TixianLogModel> arrayTixianLogModelFromData(String str) {

        Type listType = new TypeToken<ArrayList<TixianLogModel>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<TixianLogModel> arrayTixianLogModelFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<TixianLogModel>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public int getRecordId() {
        return RecordId;
    }

    public void setRecordId(int RecordId) {
        this.RecordId = RecordId;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int Uid) {
        this.Uid = Uid;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public int getPayType() {
        return PayType;
    }

    public void setPayType(int PayType) {
        this.PayType = PayType;
    }

    public String getPayAccount() {
        return PayAccount;
    }

    public void setPayAccount(String PayAccount) {
        this.PayAccount = PayAccount;
    }

    public double getApplyAmount() {
        return ApplyAmount;
    }

    public void setApplyAmount(double ApplyAmount) {
        this.ApplyAmount = ApplyAmount;
    }

    public String getApplyRemark() {
        return ApplyRemark;
    }

    public void setApplyRemark(String ApplyRemark) {
        this.ApplyRemark = ApplyRemark;
    }

    public String getApplyTime() {
        return ApplyTime;
    }

    public void setApplyTime(String ApplyTime) {
        this.ApplyTime = ApplyTime;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public int getOperatorUid() {
        return OperatorUid;
    }

    public void setOperatorUid(int OperatorUid) {
        this.OperatorUid = OperatorUid;
    }

    public String getOperatTime() {
        return OperatTime;
    }

    public void setOperatTime(String OperatTime) {
        this.OperatTime = OperatTime;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }
}
