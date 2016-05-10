package com.luoyp.brnmall.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.UserModel;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

/**
 * Created by MnZi on 2016/5/10.
 */
public class CancelOrderDialog extends DialogFragment {
    private boolean isLogin = false;
    private String uid;

    public CancelOrderDialog() {
        isLogin = App.getPref("isLogin", false);
        if (isLogin) {
            // 获取当前用户的uid
            UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
            uid = String.valueOf(userModel.getUserInfo().getUid());
        }
    }

    public static CancelOrderDialog newInstance(String oid) {
        CancelOrderDialog dialog = new CancelOrderDialog();
        Bundle bundle = new Bundle();
        bundle.putString("oid", oid);
        dialog.setArguments(bundle);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_cancel_order_tip_dialog, null);
        builder.setView(view)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Bundle bundle = getArguments();
                                String oid = bundle.getString("oid");
                                doCancelOrder(oid, uid);
                            }
                        }).setNegativeButton("取消", null);
        return builder.create();
    }

    /**
     * 取消点单
     *
     * @param oid 订单号
     * @param uid 用户id
     */
    private void doCancelOrder(String oid, String uid) {
        BrnmallAPI.doCancelOrder(oid, uid, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                KLog.json(response);

                if (TextUtils.isEmpty(response)){
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ("false".equals(jsonObject.getString("result"))){
                        return;
                    }
                    EventBus.getDefault().post("","refreshorder");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
