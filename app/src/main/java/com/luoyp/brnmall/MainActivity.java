package com.luoyp.brnmall;

import android.os.Bundle;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // PushManager.getInstance().initialize(this.getApplicationContext());
        showProgressDialog("正在加载");
    }
}
