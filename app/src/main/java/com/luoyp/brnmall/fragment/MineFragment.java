package com.luoyp.brnmall.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.activity.LoginActivity;
import com.luoyp.brnmall.activity.MyAddressActivity;
import com.luoyp.brnmall.activity.MyFavoriteActivity;
import com.luoyp.brnmall.activity.MyMoneyActivity;
import com.luoyp.brnmall.activity.MyOrderActivity;
import com.luoyp.brnmall.activity.MyProfileActivity;
import com.luoyp.brnmall.activity.MyRelationActivity;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.UserModel;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {

    private TextView nickName;
    private CircleImageView userIcon;

    private boolean isLogin = false;
    private TextView tvNickName;
    private TextView actiontoorder;
    private TextView actiontofavoritegoods;
    private TextView actiontofavoritestore;
    private TextView actiontoshipaddress;

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance(String param1) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 注册事件的接收对象
        EventBus.getDefault().register(this);

        // 获取登录标志
        isLogin = App.getPref("isLogin", false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        this.actiontoshipaddress = (TextView) view.findViewById(R.id.action_to_ship_address);
        this.actiontofavoritestore = (TextView) view.findViewById(R.id.action_to_favorite_store);
        this.actiontofavoritegoods = (TextView) view.findViewById(R.id.action_to_favorite_goods);
        this.actiontoorder = (TextView) view.findViewById(R.id.action_to_order);
        this.tvNickName = (TextView) view.findViewById(R.id.tvNickName);

//        Bundle bundle = getArguments();
//        String agrs1 = bundle.getString("agrs1");
//        TextView tv = (TextView)view.findViewById(R.id.tv_location);
//        tv.setText(agrs1);
        // 初始化用户头像
        initUserControl(view);

        //  设置点击事件
        setupClick(view);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注销事件
        EventBus.getDefault().unregister(this);
    }

    /**
     * 接收事件的方法
     *
     * @param userModel 用户对象
     */
    @Subscriber(tag = "LoginUser_tag")
    public void refreshView(UserModel userModel) {
        nickName.setText(userModel.getUserInfo().getNickName());
    }

    /**
     * 设置用户
     *
     * @param view 布局
     */
    private void initUserControl(View view) {
        // 用户头像
        userIcon = (CircleImageView) view.findViewById(R.id.ivUserIcon);
        // 用户昵称
        nickName = (TextView) view.findViewById(R.id.tvNickName);
        if (isLogin) {
            // 获取当前用户的uid
            nickName.setText(App.getPref("nicheng", ""));
            App.getPicasso().load(BrnmallAPI.userImgUrl + App.getPref("avatar", "")).error(R.mipmap.logo).placeholder(R.mipmap.logo).into(userIcon);
        } else {

        }

        // 点击头像
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin = App.getPref("isLogin", false);
                if (!isLogin) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), MyProfileActivity.class);
                    startActivity(intent);


                }
            }
        });
    }

    /**
     * 点击事件
     *
     * @param view 布局
     */
    private void setupClick(View view) {
        // 打开我的订单页面
        view.findViewById(R.id.action_to_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkLogin()) {
                    return;
                }
                startActivity(new Intent(getActivity(), MyOrderActivity.class));
            }
        });
        // 打开收藏的页面
        view.findViewById(R.id.action_to_favorite_goods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkLogin()) {
                    return;
                }
                startActivity(new Intent(getActivity(), MyFavoriteActivity.class));
            }
        });

        // 打开我的资产
        view.findViewById(R.id.action_to_my_money).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkLogin()) {
                    return;
                }
                startActivity(new Intent(getActivity(), MyMoneyActivity.class));
            }
        });

        // 打开 收货地址页面
        view.findViewById(R.id.action_to_ship_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkLogin()) {
                    return;
                }
                startActivity(new Intent(getActivity(), MyAddressActivity.class));
            }
        });
        view.findViewById(R.id.my_relation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkLogin()) {
                    return;
                }
                startActivity(new Intent(getActivity(), MyRelationActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        isLogin = App.getPref("isLogin", false);
        if (isLogin) {
            EventBus.getDefault().post("getuserinfo", "getuserinfo");
            // 获取当前用户的uid
            nickName.setText(App.getPref("nicheng", ""));
            App.getPicasso().load(BrnmallAPI.userImgUrl + App.getPref("avatar", "")).error(R.mipmap.logo).placeholder(R.mipmap.logo).into(userIcon);
        } else {
            nickName.setText("注册/登录");
            App.getPicasso().load(BrnmallAPI.userImgUrl).error(R.mipmap.logo).placeholder(R.mipmap.logo).into(userIcon);
        }
    }


    @Subscriber(tag = "getuserinfo")
    public void update(String s) {
        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        String uid = String.valueOf(userModel.getUserInfo().getUid());
        getUserInfo(uid);
    }

    public void getUserInfo(String uid) {
        BrnmallAPI.getUserInfo(uid, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                KLog.d("更新个人信息");
                if (response == null || response.isEmpty()) {

                    return;
                }

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getString("result").equals("true")) {
                        App.setPref("nicheng", jsonObject.getJSONObject("data").getString("NickName"));
                        App.setPref("zhenming", jsonObject.getJSONObject("data").getString("RealName"));
                        App.setPref("sex", jsonObject.getJSONObject("data").getInt("Gender"));
                        App.setPref("sfz", jsonObject.getJSONObject("data").getString("IdCard"));
                        App.setPref("jianjie", jsonObject.getJSONObject("data").getString("Bio"));
                        App.setPref("avatar", jsonObject.getJSONObject("data").getString("Avatar"));
                        App.setPref("addr", jsonObject.getJSONObject("data").getString("Address"));
                        App.setPref("regionId", jsonObject.getJSONObject("data").getInt("RegionId"));
                        App.getPicasso().load(BrnmallAPI.userImgUrl + App.getPref("avatar", "")).error(R.mipmap.logo).placeholder(R.mipmap.logo).into(userIcon);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
