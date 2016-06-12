package com.luoyp.brnmall.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 注册
 *
 * @author MnZi
 */
public class RegisterActivity extends BaseActivity {

    // 声明
    private TextInputEditText mAccountView, mPwdView, mConfirmPwdView, mIntroducePhoneView, mCodeView;
    private Button mRegisterBtn, mGetCodeBtn;
    private String verifyCode;// 验证码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        // 实例化
        mAccountView = (TextInputEditText) findViewById(R.id.reg_account);
        mPwdView = (TextInputEditText) findViewById(R.id.reg_pwd);
        mConfirmPwdView = (TextInputEditText) findViewById(R.id.reg_confirmpwd);
        mIntroducePhoneView = (TextInputEditText) findViewById(R.id.reg_introduce_phone);
        mCodeView = (TextInputEditText) findViewById(R.id.reg_code);
        mRegisterBtn = (Button) findViewById(R.id.btn_register);
        mGetCodeBtn = (Button) findViewById(R.id.btn_get_code);

        // 按钮添加点击监听
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

        // 点击获取验证码
        mGetCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mAccountView.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入手机号");
                    return;
                }
                if (!isPhoneValid(phone)) {
                    showToast("手机号码长度为11");
                    return;
                }
                getVerifyCode(phone);
            }
        });

        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText(getString(R.string.action_register));
        }
    }

    /**
     * 校验用户输入，并执行注册
     */
    private void attemptRegister() {
        // 重置
        mAccountView.setError(null);
        mCodeView.setError(null);
        mPwdView.setError(null);
        mConfirmPwdView.setError(null);
        mIntroducePhoneView.setError(null);
        boolean canel = false;
        View focusView = null;

        // 获取用户输入的值
        String account = mAccountView.getText().toString();
        String code = mCodeView.getText().toString();
        String pwd = mPwdView.getText().toString().trim();
        String confirmPwd = mConfirmPwdView.getText().toString().trim();
        String introducePhone = mIntroducePhoneView.getText().toString().trim();

        // 检查推荐人手机
        if (!TextUtils.isEmpty(introducePhone)) {
            if (!isPhoneValid(introducePhone)) {
                mIntroducePhoneView.setError(getString(R.string.error_invalid_phone));
                focusView = mIntroducePhoneView;
                canel = true;
            }
        }


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

        // 检查验证码
        if (TextUtils.isEmpty(code)) {
            mCodeView.setError(getString(R.string.error_field_required));
            focusView = mCodeView;
            canel = true;
        } else if (!code.equals(verifyCode)) {
            mCodeView.setError(getString(R.string.error_incorrect_code));
            focusView = mCodeView;
            canel = true;
        }

        // 检查 手机
        if (TextUtils.isEmpty(account)) {
            mAccountView.setError(getString(R.string.error_field_required));
            focusView = mAccountView;
            canel = true;
        } else if (!isPhoneValid(account)) {
            mAccountView.setError(getString(R.string.error_invalid_phone));
            focusView = mAccountView;
            canel = true;
        }

        if (canel) {
            // 定向焦点到错误处
            focusView.requestFocus();
        } else {
            // 打开提示，并执行注册
            showProgressDialog("注册中");
            doRegister(account, pwd, introducePhone);
        }
    }

    private boolean isPhoneValid(String phone) {
        // 手机长度为11
        return phone.length() == 11;
    }

    private boolean isPasswordValid(String password) {
        // 密码长度为大于等于6
        return password.length() >= 6;
    }

    /**
     * 注册请求
     *
     * @param account        手机
     * @param pwd            密码
     * @param introducePhone 推荐人手机
     */
    private void doRegister(String account, String pwd, String introducePhone) {
        BrnmallAPI.doRegister(account, pwd, introducePhone, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                // 关闭提示
                dismissProgressDialog();
                showToast("注册失败");
            }

            @Override
            public void onResponse(String response) {
                // 关闭提示
                dismissProgressDialog();
                KLog.json("Register=  ", response);
                if (TextUtils.isEmpty(response)) {
                    showToast("注册失败");
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("result").equals("false")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        showToast(jsonArray.getJSONObject(0).getString("msg"));
                    } else {
                        showToast("注册成功");
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取验证码
     *
     * @param phone 手机号
     */
    private void getVerifyCode(String phone) {
        BrnmallAPI.sendVerifyCodeToPhone(phone, "0", new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                showToast("请检查你的网络情况");
            }

            @Override
            public void onResponse(String response) {
                KLog.json("验证码  " + response);
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("result").equals("false")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        showToast(jsonArray.getJSONObject(0).getString("msg"));
                        return;
                    }
                    showToast("短信已下发到你的手机");
                    verifyCode = jsonObject.getString("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
