package com.luoyp.brnmall;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.luoyp.brnmall.activity.SearchActivity;
import com.luoyp.brnmall.fragment.BrandFragment;
import com.luoyp.brnmall.fragment.CategoryFragment;
import com.luoyp.brnmall.fragment.HomeFragment;
import com.luoyp.brnmall.fragment.MineFragment;
import com.luoyp.brnmall.fragment.ShopCarFragment;
import com.luoyp.brnmall.view.TabView;
import com.socks.library.KLog;
import com.tencent.stat.MtaSDkException;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatReportStrategy;
import com.tencent.stat.StatService;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    // PushManager.getInstance().initialize(this.getApplicationContext());

    private String[] mTitle = {"首页", "品牌", "分类", "购物车", "我的"};
    private int[] mIconSelect = {R.drawable.home_s, R.drawable.brand_s, R.drawable.order_s, R.drawable.shopcar_s, R.drawable.mine_s};
    private int[] mIconNormal = {R.drawable.home, R.drawable.brand, R.drawable.order, R.drawable.shopcar, R.drawable.mine};
    private ViewPager mViewPager;
    private TabView mTabView;
    private Map<Integer, Fragment> mFragmentMap;

    private Toolbar toolbar;
    private TextView toolbarTitle;
    private PageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initMTAConfig(false);
        String appkey = "xYUfImXA0gI7HlED";
        // 初始化并启动MTA
        // 第三方SDK必须按以下代码初始化MTA，其中appkey为规定的格式或MTA分配的代码。
        // 其它普通的app可自行选择是否调用
        try {
            // 第三个参数必须为：com.tencent.stat.common.StatConstants.VERSION
            StatService.startStatService(this, appkey,
                    com.tencent.stat.common.StatConstants.VERSION);
        } catch (MtaSDkException e) {

        }

        mFragmentMap = new HashMap<>();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("精生缘实销");
        setSupportActionBar(toolbar);

        mFragmentMap = new HashMap<>();
        mViewPager = (ViewPager) findViewById(R.id.id_view_pager);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.addOnPageChangeListener(this);
        adapter = new PageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabView = (TabView) findViewById(R.id.id_tab);
        mTabView.setViewPager(mViewPager);

    }

    @Subscriber(tag = "homemoreclick")
    public void moreClick(String tag) {
        KLog.d("tag = " + tag);

        mViewPager.setCurrentItem(2, false);

        //   onPageSelected(2);
    }

    @Subscriber(tag = "buynowClick")
    public void buynowClick(String tag) {
        KLog.d("tag = " + tag);
        mViewPager.setCurrentItem(3, false);

        //   onPageSelected(2);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void baojian(View view) {
        App.cateIndex = "34";
        EventBus.getDefault().post("34", "homemoreclick");
    }

    public void meizhuang(View view) {
        App.cateIndex = "37";
        EventBus.getDefault().post("37", "homemoreclick");
    }

    public void gehu(View view) {
        App.cateIndex = "38";
        EventBus.getDefault().post("38", "homemoreclick");
    }

    public void qingqu(View view) {
        App.cateIndex = "33";
        EventBus.getDefault().post("33", "homemoreclick");
    }

    public void more(View view) {
        startActivity(new Intent(this, SearchActivity.class));
    }
    /**
     * 根据不同的模式，建议设置的开关状态，可根据实际情况调整，仅供参考。
     *
     * @param isDebugMode 根据调试或发布条件，配置对应的MTA配置
     */
    private void initMTAConfig(boolean isDebugMode) {
        if (isDebugMode) { // 调试时建议设置的开关状态
            // 查看MTA日志及上报数据内容
            StatConfig.setDebugEnable(true);
            // 禁用MTA对app未处理异常的捕获，方便开发者调试时，及时获知详细错误信息。
            StatConfig.setAutoExceptionCaught(false);
            // StatConfig.setEnableSmartReporting(false);
            // Thread.setDefaultUncaughtExceptionHandler(new
            // UncaughtExceptionHandler() {
            //
            // @Override
            // public void uncaughtException(Thread thread, Throwable ex) {
            // logger.error("setDefaultUncaughtExceptionHandler");
            // }
            // });
            // 调试时，使用实时发送
            // StatConfig.setStatSendStrategy(StatReportStrategy.BATCH);
            // // 是否按顺序上报
            // StatConfig.setReportEventsByOrder(false);
            // // 缓存在内存的buffer日志数量,达到这个数量时会被写入db
            // StatConfig.setNumEventsCachedInMemory(30);
            // // 缓存在内存的buffer定期写入的周期
            // StatConfig.setFlushDBSpaceMS(10 * 1000);
            // // 如果用户退出后台，记得调用以下接口，将buffer写入db
            // StatService.flushDataToDB(getApplicationContext());

            // StatConfig.setEnableSmartReporting(false);
            // StatConfig.setSendPeriodMinutes(1);
            // StatConfig.setStatSendStrategy(StatReportStrategy.PERIOD);
        } else { // 发布时，建议设置的开关状态，请确保以下开关是否设置合理
            // 禁止MTA打印日志
            StatConfig.setDebugEnable(false);
            // 根据情况，决定是否开启MTA对app未处理异常的捕获
            StatConfig.setAutoExceptionCaught(false);
            // 选择默认的上报策略
            StatConfig.setStatSendStrategy(StatReportStrategy.APP_LAUNCH);
        }
    }

    private Fragment getFragment(int position) {
        Fragment fragment = mFragmentMap.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new BrandFragment();
                    break;
                case 2:
                    fragment = new CategoryFragment();
                    break;
                case 3:
                    fragment = new ShopCarFragment();
                    break;
                case 4:
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
        toolbar.setVisibility(View.VISIBLE);

        if (position == 0) {
            toolbarTitle.setText("精生缘实销");
            return;
        }
        if (position == 4) {
            toolbar.setVisibility(View.GONE);

            toolbarTitle.setText("");
            return;
        }
        toolbarTitle.setText(mTitle[position]);

        if (position == 3) {
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