package com.luoyp.brnmall.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.UserModel;
import com.luoyp.xlibrary.net.OkHttpClientManager;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

public class AddMyAddressActivity extends BaseActivity {

    private EditText mNameView, mPhoneView, mProvinceView, mDiquView, mDetailView;
    private Button mSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_my_address);

        mNameView = (EditText) findViewById(R.id.my_name);
        mPhoneView = (EditText) findViewById(R.id.my_phone);
        mProvinceView = (EditText) findViewById(R.id.my_province);
        mDiquView = (EditText) findViewById(R.id.my_diqu);
        mDetailView = (EditText) findViewById(R.id.my_detail);
        mSaveBtn = (Button) findViewById(R.id.btn_save);

        // 点击保存
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSave();
            }
        });

        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("新增收货地址");
        }
        ImageButton imageButton = (ImageButton) findViewById(R.id.topbar_right);
        assert imageButton != null;
        imageButton.setVisibility(View.GONE);
    }

    // 保存
    private void attemptSave() {
        if (isCheck()) {
            String name = mNameView.getText().toString().trim();
            String phone = mPhoneView.getText().toString().trim();
            String city = mProvinceView.getText().toString().trim();
            String diqu = mDiquView.getText().toString().trim();
            String detail = mDetailView.getText().toString().trim();
            // 获取当前用户的uid
            UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
            String uid = String.valueOf(userModel.getUserInfo().getUid());
            String regionid = String.valueOf(userModel.getUserInfo().getRegionId());
            OkHttpClientManager.Param[] params = {
                    new OkHttpClientManager.Param("uid", uid),
                    new OkHttpClientManager.Param("aid", ""),
                    new OkHttpClientManager.Param("name", name),
                    new OkHttpClientManager.Param("regionId", regionid),
                    new OkHttpClientManager.Param("address", city + "|" + diqu + "|" + detail),
                    new OkHttpClientManager.Param("mobile", phone),
                    new OkHttpClientManager.Param("phone", ""),
                    new OkHttpClientManager.Param("zipcode", ""),
                    new OkHttpClientManager.Param("email", ""),
                    new OkHttpClientManager.Param("isDefault", "0")
            };
            doSave(params);
        } else {
            showToast("不能留空、手机号长度为11位");
        }
    }

    // 检查用户输入
    private boolean isCheck() {
        boolean cancel = true;
        String name = mNameView.getText().toString().trim();
        String phone = mPhoneView.getText().toString().trim();
        String city = mProvinceView.getText().toString().trim();
        String diqu = mDiquView.getText().toString().trim();
        String detail = mDetailView.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            cancel = false;
            return cancel;
        }
        if (TextUtils.isEmpty(phone) || phone.length() < 11) {
            cancel = false;
            return cancel;
        }

        if (TextUtils.isEmpty(city)) {
            cancel = false;
            return cancel;
        }

        if (TextUtils.isEmpty(diqu)) {
            cancel = false;
            return cancel;
        }

        if (TextUtils.isEmpty(detail)) {
            cancel = false;
            return cancel;
        }

        return cancel;
    }

    /**
     * 新增收货地址
     *
     * @param params 参数
     */
    private void doSave(OkHttpClientManager.Param[] params) {
        BrnmallAPI.addMyAddress(params, new ApiCallback<String>() {
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
                    EventBus.getDefault().post("", "refreshAdderss");
                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    showToast(dataArray.getJSONObject(0).getString("msg"));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
