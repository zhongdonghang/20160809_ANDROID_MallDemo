package com.luoyp.brnmall.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.activity.LoginActivity;
import com.luoyp.brnmall.activity.MyOrderActivity;
import com.luoyp.brnmall.model.UserModel;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {

    private TextView nickName;
    private ImageView userIcon;

    private boolean isLogin = false;
    private ImageView ivUserIcon;
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
        this.ivUserIcon = (ImageView) view.findViewById(R.id.ivUserIcon);

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
        userIcon = (ImageView) view.findViewById(R.id.ivUserIcon);
        // 用户昵称
        nickName = (TextView) view.findViewById(R.id.tvNickName);
        if (isLogin) {
            // 获取当前用户的uid
            UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
            nickName.setText(userModel.getUserInfo().getNickName());
        } else {

        }

        // 点击头像
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
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
        // 打开收藏的商品页面
        view.findViewById(R.id.action_to_favorite_goods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 打开收藏的店铺页面
        view.findViewById(R.id.action_to_favorite_store).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 打开 收货地址页面
        view.findViewById(R.id.action_to_ship_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
