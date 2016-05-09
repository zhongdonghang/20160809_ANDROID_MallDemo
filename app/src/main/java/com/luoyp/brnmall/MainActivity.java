package com.luoyp.brnmall;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.luoyp.brnmall.fragment.CategoryFragment;
import com.luoyp.brnmall.fragment.HomeFragment;
import com.luoyp.brnmall.fragment.MineFragment;
import com.luoyp.brnmall.fragment.ShopCarFragment;
import com.luoyp.brnmall.view.TabView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    // PushManager.getInstance().initialize(this.getApplicationContext());

    private String[] mTitle = {"首页", "分类", "购物车", "我的"};
    private int[] mIconSelect = {R.drawable.home_s, R.drawable.order_s, R.drawable.shopcar_s, R.drawable.mine};
    private int[] mIconNormal = {R.drawable.home, R.drawable.order, R.drawable.shopcar, R.drawable.mine_s};
    private ViewPager mViewPager;
    private TabView mTabView;
    private Map<Integer, Fragment> mFragmentMap;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mFragmentMap = new HashMap<>();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragmentMap = new HashMap<>();
        mViewPager = (ViewPager) findViewById(R.id.id_view_pager);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        mTabView = (TabView) findViewById(R.id.id_tab);
        mTabView.setViewPager(mViewPager);

    }

    private Fragment getFragment(int position) {
        Fragment fragment = mFragmentMap.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new CategoryFragment();
                    break;
                case 2:
                    fragment = new ShopCarFragment();
                    break;
                case 3:
                    fragment = new MineFragment();
                    break;
            }
            mFragmentMap.put(position, fragment);
        }

        return fragment;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            toolbar.setTitle("精生缘实销");
            return;
        }
        if (position == 3) {
            toolbar.setTitle("");
            return;
        }
        toolbar.setTitle(mTitle[position]);

        if (position == 2) {
            if (checkLogin()) {

            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class PageAdapter extends FragmentPagerAdapter implements TabView.OnItemIconTextSelectListener {

        public PageAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getFragment(position);
        }

        @Override
        public int[] onIconSelect(int position) {
            int icon[] = new int[2];
            icon[0] = mIconSelect[position];
            icon[1] = mIconNormal[position];

            return icon;
        }

        @Override
        public String onTextSelect(int position) {
            return mTitle[position];

            //  return "";
        }

        @Override
        public int getCount() {
            return mTitle.length;
        }
    }

}