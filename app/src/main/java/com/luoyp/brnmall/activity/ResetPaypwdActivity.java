package com.luoyp.brnmall.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResetPaypwdActivity extends BaseActivity {

    // 声明
    private TextInputEditText mPwdView, mConfirmPwdView;
    private Button mResetBtn;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_reset_paypwd);

        // 实例化
        mPwdView = (TextInputEditText) findViewById(R.id.reg_pwd);
        mConfirmPwdView = (TextInputEditText) findViewById(R.id.reg_confirmpwd);
        mResetBtn = (Button) findViewById(R.id.btn_reset);

        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("支付密码");
        }

        // 按钮添加点击监听
        mResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptResetPassword();
            }
        });

        uid = App.getPref("uid","");
    }

    /**
     * 校验用户输入，并执行注册
     */
    private void attemptResetPassword() {
        // 重置
        mPwdView.setError(null);
        mConfirmPwdView.setError(null);
        boolean canel = false;
        View focusView = null;

        // 获取用户输入的值
        String pwd = mPwdView.getText().toString().trim();
        String confirmPwd = mConfirmPwdView.getText().toString().trim();

        // 检查确认密码
        if (TextUtils.isEmpty(confirmPwd)) {
            mConfirmPwdView.setError(getString(R.string.error_field_required));
            focusView = mConfirmPwdView;
            canel = true;
        } else if (!confirmPwd.equals(pwd)) {
            mConfirmPwdView.setError(getString(R.string.error_pwd_confirmpwd));
            focusView = mConfirmPwdView;
            canel = true;
        }

        // 检查 密码
        if (TextUtils.isEmpty(pwd)) {
            mPwdView.setError(getString(R.string.error_field_required));
            focusView = mPwdView;
            canel = true;
        } else if (!isPasswordValid(pwd)) {
            mPwdView.setError(getString(R.string.error_invalid_password));
            focusView = mPwdView;
            canel = true;
        }

        if (canel) {
            // 定向焦点到错误处
            focusView.requestFocus();
        } else {
            // 打开提示，并执行注册
            showProgressDialog("");
            doReset(uid,pwd);
        }
    }

    private boolean isPasswordValid(String password) {
        // 密码长度为大于等于6
        return password.length() >= 6;
    }

    /**
     * 设置支付密码请求
     * @param uid
     * @param pwd
     */
    private void doReset(String uid,String pwd) {
        BrnmallAPI.resetPayPwd(uid, pwd, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                // 关闭提示
                dismissProgressDialog();
                showToast("请检查你的网络情况");
            }

            @Override
            public void onResponse(String response) {
                // 关闭提示
                dismissProgressDialog();
                KLog.json("支付密码=  ", response);
                if (TextUtils.isEmpty(response)) {
                    showToast("设置密码失败");
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("result").equals("false")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        showToast(jsonArray.getJSONObject(0).getString("msg"));
                    } else {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        showToast(jsonArray.getJSONObject(0).getString("msg"));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
