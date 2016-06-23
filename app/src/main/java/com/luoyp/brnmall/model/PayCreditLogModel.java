package com.luoyp.brnmall.model;

/**
 * Created by lyp3314@gmail.com on 16/6/23.
 */

public class PayCreditLogModel {
    private String userAmount = "";
    private String frozenAmount = "";
    private String actionTime = "";
    private String actionDes = "";

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getUserAmount() {
        return userAmount;
    }

    public void setUserAmount(String userAmount) {
        this.userAmount = userAmount;
    }

    public String getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(String frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public String getActionDes() {
        return actionDes;
    }

    public void setActionDes(String actionDes) {
        this.actionDes = actionDes;
    }
}
