package com.luoyp.brnmall;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.luoyp.brnmall.fragment.CategoryFragment;
import com.luoyp.brnmall.fragment.HomeFragment;
import com.luoyp.brnmall.fragment.MineFragment;
import com.luoyp.brnmall.fragment.ShopCarFragment;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    // PushManager.getInstance().initialize(this.getApplicationContext());

    int lastSelectedPosition = 0;
    private BottomNavigationBar bottomNavigationBar;
    private String TAG = MainActivity.class.getSimpleName();
    private HomeFragment homeFragment;
    private CategoryFragment categoryFragment;
    private ShopCarFragment shopCarFragment;
    private MineFragment mineFragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_CLASSIC);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBarBackgroundColor("#EFEFEF");
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.home, "首页").setActiveColor(R.color.colorPrimary).setInActiveColor("#9D9D9D"))
                .addItem(new BottomNavigationItem(R.drawable.order, "分类").setActiveColor(R.color.colorPrimary).setInActiveColor("#9D9D9D"))
                .addItem(new BottomNavigationItem(R.drawable.shopcar, "购物车").setActiveColor(R.color.colorPrimary).setInActiveColor("#9D9D9D"))
                .addItem(new BottomNavigationItem(R.drawable.mine, "我的").setActiveColor(R.color.colorPrimary).setInActiveColor("#9D9D9D"))
                .setFirstSelectedPosition(lastSelectedPosition)//设置默认选中
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        homeFragment = HomeFragment.newInstance("首页");
        transaction.replace(R.id.tb, homeFragment);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        Log.d(TAG, "onTabSelected() called with: " + "position = [" + position + "]");
        FragmentManager fm = this.getFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance("首页");

                }
                transaction.replace(R.id.tb, homeFragment);
                toolbar.setTitle("首页");

                break;
            case 1:
                if (categoryFragment == null) {
                    categoryFragment = CategoryFragment.newInstance("分类");
                }
                transaction.replace(R.id.tb, categoryFragment);
                toolbar.setTitle("分类");
                break;
            case 2:
                if (shopCarFragment == null) {
                    shopCarFragment = ShopCarFragment.newInstance("购物车");
                }
                transaction.replace(R.id.tb, shopCarFragment);
                toolbar.setTitle("购物车");
                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = mineFragment.newInstance("我的");
                }
                transaction.replace(R.id.tb, mineFragment);
                toolbar.setTitle("我的");
                break;
            default:
                break;
        }
        // 事务提交
        transaction.commit();
    }


    @Override
    public void onTabUnselected(int position) {
        Log.d(TAG, "onTabUnselected() called with: " + "position = [" + position + "]");
    }

    @Override
    public void onTabReselected(int position) {

    }
}