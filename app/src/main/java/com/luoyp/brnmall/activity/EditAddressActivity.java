package com.luoyp.brnmall.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
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
import com.luoyp.brnmall.model.MyAddressModel;
import com.luoyp.brnmall.model.UserModel;
import com.luoyp.xlibrary.net.OkHttpClientManager;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

class Area {
    private String id = "";
    private String name = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

public class EditAddressActivity extends BaseActivity {

    private EditText mNameView, mPhoneView, mProvinceView, mChengshi, mDiquView, mDetailView;
    private Button mSaveBtn, mDelBtn;

    private String uid;
    private MyAddressModel model;
    private String[] province = {"北京市", "天津市", "河北省", "山西省", "内蒙古自治区", "辽宁省", "吉林省", "黑龙江省", "上海市", "江苏省", "浙江省", "安徽省", "福建省", "江西省", "山东省", "河南省", "湖北省", "湖南省", "广东省", "广西", "海南省", "重庆市", "四川省", "贵州省", "云南省", "西藏自治区", "陕西省", "甘肃省", "青海省", "宁夏自治区", "新疆自治区", "台湾省", "香港行政区", "澳门行政区"};
    private List<String> provinceList = new ArrayList<>();
    private List<String> city = new ArrayList<>();
    private List<String> diqu = new ArrayList<>();

    private List<Area> cityArea = new ArrayList<>();
    private List<Area> diquArea = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_address);

        // 获取当前用户的uid
        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        uid = String.valueOf(userModel.getUserInfo().getUid());

        mNameView = (EditText) findViewById(R.id.my_name);
        mPhoneView = (EditText) findViewById(R.id.my_phone);
        mProvinceView = (EditText) findViewById(R.id.my_province);
        mDiquView = (EditText) findViewById(R.id.my_diqu);
        mDetailView = (EditText) findViewById(R.id.my_detail);
        mSaveBtn = (Button) findViewById(R.id.btn_save);
        mDelBtn = (Button) findViewById(R.id.btn_delete);
        mChengshi = (EditText) findViewById(R.id.my_chengshi);

        mProvinceView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDialog("选择省份", province, "1");
                }
            }
        });
        mProvinceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("选择省份", province, "1");
            }
        });

        mChengshi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDialog("选择城市", city.toArray(new String[city.size()]), "2");
                }
            }
        });

        mChengshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("选择城市", city.toArray(new String[city.size()]), "2");
            }
        });

        mDiquView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDialog("选择地区", diqu.toArray(new String[diqu.size()]), "3");
                }
            }
        });
        mDiquView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("选择地区", diqu.toArray(new String[diqu.size()]), "3");
            }
        });

        //接收intent传递过来的数据
        model = (MyAddressModel) getIntent().getSerializableExtra("address");
        mNameView.setText(model.getName());
        mPhoneView.setText(model.getMobile());
        mProvinceView.setText(model.getProvinceName());
        mChengshi.setText(model.getCityName());
        mDiquView.setText(model.getCountyName());
        mDetailView.setText(model.getAddress());

        getcity(model.getProvinceId());
        getDiqu(model.getCityId());
        // 点击保存
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSave();
            }
        });

        mDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doDelete(uid, model.getAid());
            }
        });

        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("编辑收货地址");
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

            OkHttpClientManager.Param[] params = {
                    new OkHttpClientManager.Param("uid", uid),
                    new OkHttpClientManager.Param("aid", model.getAid()),
                    new OkHttpClientManager.Param("name", name),
                    new OkHttpClientManager.Param("regionId", model.getRegionId()),
                    new OkHttpClientManager.Param("address", detail),
                    new OkHttpClientManager.Param("mobile", phone),
                    new OkHttpClientManager.Param("phone", ""),
                    new OkHttpClientManager.Param("zipcode", ""),
                    new OkHttpClientManager.Param("email", ""),
                    new OkHttpClientManager.Param("isDefault", model.getIsDefault())
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
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ("false".equals(jsonObject.getString("result"))) {
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

    private void doDelete(String uid, String said) {
        BrnmallAPI.deleteAddress(uid, said, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                KLog.json(response);
                if (TextUtils.isEmpty(response)) return;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ("false".equals(jsonObject.getString("result"))) return;

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

    public void showDialog(String name, String[] data, final String type) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(EditAddressActivity.this);
        builderSingle.setIcon(R.mipmap.logo);
        builderSingle.setTitle(name);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                EditAddressActivity.this,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.addAll(data);

        builderSingle.setNegativeButton(
                "取消",
                new DialogInterface.OnClickListener()

                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener()

                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ("1".equals(type)) {
                            KLog.d("id=" + (which + 1) + "name=" + province[which]);
                            mProvinceView.setText(province[which]);
                            getcity((which + 1) + "");
                        }
                        if ("2".equals(type)) {
                            KLog.d("id=" + cityArea.get(which).getId() + "name=" + cityArea.get(which).getName());
                            mChengshi.setText(cityArea.get(which).getName());
                            getDiqu(cityArea.get(which).getId());
                        }
                        if ("3".equals(type)) {
                            KLog.d("id=" + diquArea.get(which).getId() + "name=" + diquArea.get(which).getName());
                            mDiquView.setText(diquArea.get(which).getName());
                            model.setRegionId(diquArea.get(which).getId());
                        }


                    }
                }

        );
        builderSingle.show();
    }

    public void getDiqu(String id) {
        diquArea.clear();
        diqu.clear();
        BrnmallAPI.getDiqu(id, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                KLog.d("根据城市id返回json " + response);
                try {
                    JSONObject json = new JSONObject(response);
                    if ("success".equals(json.getString("state"))) {
                        JSONArray jsonArray = json.getJSONArray("content");
                        int count = jsonArray.length();
                        for (int i = 0; i < count; i++) {
                            Area area = new Area();
                            String name = jsonArray.getJSONObject(i).getString("name");
                            String id = jsonArray.getJSONObject(i).getString("id");
                            area.setId(id);
                            area.setName(name);
                            diquArea.add(area);
                            diqu.add(name);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getcity(String id) {
        city.clear();
        cityArea.clear();
        BrnmallAPI.getCity(id, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                KLog.d("根据省份id返回json " + response);
                try {
                    JSONObject json = new JSONObject(response);
                    if ("success".equals(json.getString("state"))) {
                        JSONArray jsonArray = json.getJSONArray("content");
                        int count = jsonArray.length();
                        for (int i = 0; i < count; i++) {
                            Area area = new Area();
                            String name = jsonArray.getJSONObject(i).getString("name");
                            String id = jsonArray.getJSONObject(i).getString("id");
                            area.setId(id);
                            area.setName(name);
                            cityArea.add(area);
                            city.add(name);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
