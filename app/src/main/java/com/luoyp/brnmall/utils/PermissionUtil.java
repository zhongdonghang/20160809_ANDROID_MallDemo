package com.luoyp.brnmall.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

/**
 * 权限管理
 * Created by MnZi on 2016/5/19.
 */
public class PermissionUtil {

    /**
     * 检查是否具有权限
     *
     * @param activity   activity
     * @param permission 权限
     * @return true/false
     */
    public static boolean hasSelfPermission(Activity activity, String permission) {
        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 申请权限
     *
     * @param activity   Activity
     * @param permission 权限
     */
    public static void requestPermission(final Activity activity, String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, 2233);
        } else {
            showAskDialog(activity);
        }
    }

    public static void showAskDialog(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setMessage("当前应用缺少相应的权限\n" +
                        "请点击“设置”-“权限”-打开所需权限")
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + activity.getPackageName()));
                        activity.startActivity(intent);
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

}
