package com.luoyp.brnmall.fragment;


import android.support.v4.app.Fragment;

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
}
