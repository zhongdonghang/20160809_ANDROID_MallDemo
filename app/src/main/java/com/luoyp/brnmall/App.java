package com.luoyp.brnmall;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;

import com.luoyp.brnmall.model.ShopCartModel;
import com.socks.library.KLog;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Map;

/**
 * Created by lyp3314@gmail.com on 16/4/13.
 */
public class App extends Application {
    //临时保存购物车
    public static ShopCartModel shopCar;
    public static Picasso picasso;
    static Context _context;
    static Resources _resource;
    private static String PREF_NAME = "brnmall.pref";
    private static boolean sIsAtLeastGB;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sIsAtLeastGB = true;
        }
    }

    public static synchronized App context() {
        return (App) _context;
    }

    public static Resources resources() {
        return _resource;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void apply(SharedPreferences.Editor editor) {
        if (sIsAtLeastGB) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    public static void setPref(String key, int value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        apply(editor);
    }

    public static void setPref(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        apply(editor);
    }

    public static void setPref(String key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        apply(editor);
    }

    public static void clearPref() {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.clear();
        editor.commit();
    }

    public static boolean getPref(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public static String getPref(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    public static int getPref(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    public static long getPref(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    public static float getPref(String key, float defValue) {
        return getPreferences().getFloat(key, defValue);
    }

    //  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static SharedPreferences getPreferences() {
        SharedPreferences pre = context().getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE);
        return pre;
    }

    public static String getResString(int id) {
        return _resource.getString(id);
    }

    public static Picasso getPicasso() {
        if (picasso == null) {
            File cacheDir = new File(Environment.getExternalStorageDirectory().toString() + "/brnmall/imgcache");
            //   TLog.i(cacheDir.toString());
            if (!cacheDir.exists()) {
                cacheDir.mkdir();
            }

            Downloader downloader = new OkHttpDownloader(cacheDir, Integer.MAX_VALUE);
            picasso = new Picasso.Builder(_context)
                    .downloader(downloader)
                    .build();
            return picasso;
        }
        return picasso;
    }

    public static String getAllPrefs() {
        StringBuffer stringBuffer = new StringBuffer();
        Map<String, ?> keys = getPreferences().getAll();
        stringBuffer.append("all presf: ");
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            stringBuffer.append("--" + entry.getKey() + ":" + entry.getValue().toString());
        }
        return stringBuffer.toString();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();
        _resource = _context.getResources();
        // CrashHandler crashHandler = CrashHandler.getInstance();
        //  crashHandler.init(getApplicationContext());
        KLog.init(BuildConfig.DEBUG);
    }
}
