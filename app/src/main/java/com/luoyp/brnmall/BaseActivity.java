package com.luoyp.brnmall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.luoyp.brnmall.activity.LoginActivity;
import com.luoyp.xlibrary.widgets.CustomProgressDialog;
import com.squareup.picasso.Picasso;

/**
 * Created by lyp3314@gmail.com on 16/4/13.
 */
public class BaseActivity extends AppCompatActivity {
    public static final String TAG = "brnmallTag";
    public final String spf = "brnmallspf";
    //是否手动刷新
    public boolean isRefresh = false;
    AlertDialog loginDialog;
    private CustomProgressDialog progressDialog = null;

    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public Picasso getPicasso() {
        return App.getPicasso();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(this);
        }
        progressDialog.setMessage(msg);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            // progressDialog = null;
        }
    }

    public void showToast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 点击空白位置关闭虚拟键盘
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        try {
            return imm.hideSoftInputFromWindow(this.getCurrentFocus()
                    .getWindowToken(), 0);
        } catch (Exception e) {
            return false;
        }

    }

    public Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void back(View view) {
        onBackPressed();
    }

    public void add(View view) {
        onBackPressed();
    }

    public boolean checkLogin() {
        boolean isLogin = App.getPref("isLogin", false);
        if (!isLogin) {
            startActivity(new Intent(this, LoginActivity.class));
            return false;
        }
        return true;
    }

}
