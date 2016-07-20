package com.luoyp.brnmall.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;

public class CommentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_comment);

        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("评 价");
        }
    }
}
