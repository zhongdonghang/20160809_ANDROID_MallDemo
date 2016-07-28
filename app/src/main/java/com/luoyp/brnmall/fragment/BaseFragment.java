package com.luoyp.brnmall.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.widget.Toast;

import com.luoyp.brnmall.App;
import com.luoyp.brnmall.activity.LoginActivity;
import com.luoyp.xlibrary.widgets.CustomProgressDialog;

/**
 * Created by lyp3314@gmail.com on 16/4/14.
 */
public class BaseFragment extends Fragment {

    private CustomProgressDialog progressDialog = null;

    public void showProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(getActivity());
        }

        progressDialog.setMessage(msg);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            // progressDialog = null;
        }
    }

    public void showToast(String msg) {
        Toast toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public boolean checkLogin() {
        boolean isLogin = App.getPref("isLogin", false);
        if (!isLogin) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return false;
        }
        return true;
    }
}
