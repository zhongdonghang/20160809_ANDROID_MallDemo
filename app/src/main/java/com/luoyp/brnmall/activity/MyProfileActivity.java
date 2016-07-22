package com.luoyp.brnmall.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.UserModel;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileActivity extends BaseActivity {

    // 拍照成功，读取相册成功，裁减成功
    private final int ALBUM_OK = 1, CAMERA_OK = 2, CUT_OK = 3;
    ImageButton btn;
    private CircleImageView ivavatar;
    private android.widget.RelativeLayout reavatar;
    private TextView tvnicheng;
    private TextView tvnickname;
    private android.widget.RelativeLayout renicheng;
    private TextView tvrealname;
    private TextView tvzhenming;
    private android.widget.RelativeLayout rerealname;
    private TextView tvtempsex;
    private TextView tvsex;
    private android.widget.RelativeLayout resex;
    private TextView tvtempregion;
    private TextView tvsfz;
    private android.widget.RelativeLayout resfz;
    private TextView tvtempsign;
    private TextView tvjianjie;
    private android.widget.RelativeLayout rejianjie;
    private android.widget.RelativeLayout relogout;
    private File file;

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = "";
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_profile);
        // 定义拍照后存放图片的文件位置和名称，使用完毕后可以方便删除
        file = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        file.delete();// 清空之前的文件

        this.relogout = (RelativeLayout) findViewById(R.id.re_logout);
        this.rejianjie = (RelativeLayout) findViewById(R.id.re_jianjie);
        this.tvjianjie = (TextView) findViewById(R.id.tv_jianjie);
        this.tvtempsign = (TextView) findViewById(R.id.tv_temp_sign);
        this.resfz = (RelativeLayout) findViewById(R.id.re_sfz);
        this.tvsfz = (TextView) findViewById(R.id.tv_sfz);
        this.tvtempregion = (TextView) findViewById(R.id.tv_temp_region);
        this.resex = (RelativeLayout) findViewById(R.id.re_sex);
        this.tvsex = (TextView) findViewById(R.id.tv_sex);
        this.tvtempsex = (TextView) findViewById(R.id.tv_temp_sex);
        this.rerealname = (RelativeLayout) findViewById(R.id.re_realname);
        this.tvzhenming = (TextView) findViewById(R.id.tv_zhenming);
        this.tvrealname = (TextView) findViewById(R.id.tv_realname);
        this.renicheng = (RelativeLayout) findViewById(R.id.re_nicheng);
        this.tvnickname = (TextView) findViewById(R.id.tv_nickname);
        this.tvnicheng = (TextView) findViewById(R.id.tv_nicheng);
        this.reavatar = (RelativeLayout) findViewById(R.id.re_avatar);
        this.ivavatar = (CircleImageView) findViewById(R.id.iv_avatar);


        tvnickname.setText(App.getPref("nicheng", ""));
        tvzhenming.setText(App.getPref("zhenming", ""));
        if (App.getPref("sex", 0) == 0) {
            tvsex.setText("其他");
        }
        if (App.getPref("sex", 0) == 1) {
            tvsex.setText("男");
        }
        if (App.getPref("sex", 0) == 2) {
            tvsex.setText("女");
        }

        tvsfz.setText(App.getPref("sfz", ""));
        tvjianjie.setText(App.getPref("jianjie", ""));

        App.getPicasso().load(BrnmallAPI.userImgUrl + App.getPref("avatar", "")).error(R.mipmap.logo).placeholder(R.mipmap.logo).into(ivavatar);

        // 设置topbar
        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("个人中心");
        }
        btn = (ImageButton) findViewById(R.id.topbar_right);
        btn.setBackgroundResource(R.drawable.save);

        relogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyProfileActivity.this);
                builder.setMessage("确定注销当前用户?");

                builder.setTitle("退出登录");
                builder.setCancelable(false);
                builder.setPositiveButton("我点错了", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("注销用户", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        App.setPref("isLogin", false);
                        App.setPref("nicheng", "");
                        App.setPref("zhenming", "");
                        App.setPref("sex", -1);
                        App.setPref("sfz", "");
                        App.setPref("jianjie", "");
                        App.setPref("avatar", "");
                        App.setPref("regionId", -1);
                        App.setPref("addr", "");
                        MyProfileActivity.this.finish();
                    }
                });
                builder.create().show();
            }
        });
    }

    public void showInputDialog(final View view) {
        if (view.getId() == R.id.re_avatar) {
            List<String> mAnimals = new ArrayList<String>();
            mAnimals.add("拍照");
            mAnimals.add("相册");

            //Create sequence of items
            final CharSequence[] Animals = mAnimals.toArray(new String[mAnimals.size()]);
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("选择头像");
            dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    if (item == 0) {
                        KLog.d("拍照");
                        // 来自相机
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 下面这句指定调用相机拍照后的照片存储的路径
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                        startActivityForResult(cameraIntent, CAMERA_OK);// CAMERA_OK是用作判断返回结果的标识
                    } else {
                        KLog.d("相册");
                        // 来自相册
                        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);

                        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(albumIntent, ALBUM_OK);
                    }
                }
            });
            //Create alert dialog object via builder
            AlertDialog alertDialogObject = dialogBuilder.create();
            alertDialogObject.setCanceledOnTouchOutside(false);
            alertDialogObject.setCancelable(false);
            //Show the dialog
            alertDialogObject.show();
            return;
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_input, null);
        dialog.setView(layout);
        dialog.setCancelable(false);
        final EditText et_search = (EditText) layout.findViewById(R.id.searchC);
        if (view.getId() == R.id.re_nicheng) {
            dialog.setTitle("请输入昵称");
            et_search.setHint("昵称");
        }
        if (view.getId() == R.id.re_realname) {
            dialog.setTitle("请输入真名");
            et_search.setHint("真名");
        }
        if (view.getId() == R.id.re_sex) {

            List<String> mAnimals = new ArrayList<String>();
            mAnimals.add("男");
            mAnimals.add("女");
            mAnimals.add("其他");

            //Create sequence of items
            final CharSequence[] Animals = mAnimals.toArray(new String[mAnimals.size()]);
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("选择性别");

            dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    String selectedText = Animals[item].toString();  //Selected item in listview
                    tvsex.setText(selectedText);
                    btn.setVisibility(View.VISIBLE);
                }
            });
            //Create alert dialog object via builder
            AlertDialog alertDialogObject = dialogBuilder.create();
            alertDialogObject.setCanceledOnTouchOutside(false);
            alertDialogObject.setCancelable(false);
            //Show the dialog
            alertDialogObject.show();
            return;
        }
        if (view.getId() == R.id.re_sfz) {
            dialog.setTitle("请输入身份证");
            et_search.setHint("身份证");
            et_search.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if (view.getId() == R.id.re_jianjie) {
            dialog.setTitle("请输入个人简介");
            et_search.setHint("个人简介");
        }
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                String searchC = et_search.getText().toString();
                if (searchC.isEmpty()) {
                    return;
                }
                btn.setVisibility(View.VISIBLE);

                if (view.getId() == R.id.re_nicheng) {
                    tvnickname.setText(searchC);
                }
                if (view.getId() == R.id.re_realname) {
                    if (searchC.length() < 5) {
                        tvzhenming.setText(searchC);
                    } else {
                        showToast("真名不能超过5个字符");
                        return;
                    }
                    tvzhenming.setText(searchC);
                }

                if (view.getId() == R.id.re_sfz) {
                    if (searchC.length() == 15 || searchC.length() == 18) {
                        tvsfz.setText(searchC);
                    } else {
                        showToast("请输入15或18位身份证号码");
                        return;
                    }

                }
                if (view.getId() == R.id.re_jianjie) {
                    tvjianjie.setText(searchC);
                }
            }
        });

        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }

        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("requestCode = " + requestCode);
        switch (requestCode) {
            // 如果是直接从相册获取
            case ALBUM_OK:
                //从相册中获取到图片了，才执行裁剪动作
                if (data != null) {
                    clipPhoto(data.getData());
                }
                break;
            // 如果是调用相机拍照时
            case CAMERA_OK:
                // 当拍照到照片时进行裁减，否则不执行操作
                if (file.exists()) {
                    clipPhoto(Uri.fromFile(file));//开始裁减图片
                }
                break;
            // 取得裁剪后的图片，这里将其设置到imageview中
            case CUT_OK:
                /**
                 * 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，
                 * 要重新裁剪，丢弃 当前功能时，会报NullException
                 */
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        //   KLog.d(bitmapToBase64(photo));
                        if (photo != null) {
                            String base64 = bitmapToBase64(photo);
                            updateAvatar(base64);

                        }
                        file.delete();//设置成功后清除之前的照片文件
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void clipPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop = true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例，这里设置的是正方形（长宽比为1:1）
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CUT_OK);
    }

    public void add(View view) {
        showProgressDialog("正在保存信息");
        KLog.d("update profile");
        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        String uid = String.valueOf(userModel.getUserInfo().getUid());
        String sex = "0";
        if ("男".equals(tvsex.getText())) {
            sex = "1";
        }
        if ("女".equals(tvsex.getText())) {
            sex = "2";

        }

        BrnmallAPI.userEdit(uid, tvnickname.getText().toString(), tvzhenming.getText().toString(), sex, tvsfz.getText().toString(), "", App.getPref("regionId", 1) + "", tvjianjie.getText().toString(), App.getPref("addr", ""), new ApiCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        dismissProgressDialog();
                        showToast("网络异常,请稍后再试");
                    }

                    @Override
                    public void onResponse(String response) {
                        dismissProgressDialog();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            if (jsonObject.getString("result").equals("true")) {
                                showToast("个人信息更新成功");
                                EventBus.getDefault().post("update", "getuserinfo");
                                MyProfileActivity.this.finish();
                                return;
                            }
                            showToast("更新失败,请稍后再试");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

        );
    }

    public void updateAvatar(String avatar) {
        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        String uid = String.valueOf(userModel.getUserInfo().getUid());

        BrnmallAPI.userAvatarEdit(uid, avatar, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getString("result").equals("true")) {
                        showToast("头像更新成功");
                        EventBus.getDefault().post("update", "getuserinfo");
                        MyProfileActivity.this.finish();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 打开修改登录密码页面
    public void toLoginPwd(View view) {
        startActivity(new Intent(this, ResetPasswordActivity.class));
    }

    // 打开修改支付密码页面
    public void toPayPwd(View view) {

        startActivity(new Intent(this, ResetPaypwdActivity.class));
    }

}
