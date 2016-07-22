package com.luoyp.brnmall.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luoyp.brnmall.App;
import com.luoyp.brnmall.BaseActivity;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.UserModel;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

public class CommentActivity extends BaseActivity {

    private android.widget.LinearLayout imgll;
    private android.widget.RatingBar goodsStars;
    private android.widget.EditText commentEt;
    private android.widget.RatingBar goodsdesStars;
    private android.widget.RatingBar serviceStars;
    private android.widget.RatingBar beisongStars;
    private android.widget.Button btncomment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_comment);
        this.btncomment = (Button) findViewById(R.id.btn_comment);
        this.beisongStars = (RatingBar) findViewById(R.id.beisongStars);
        this.serviceStars = (RatingBar) findViewById(R.id.serviceStars);
        this.goodsdesStars = (RatingBar) findViewById(R.id.goodsdesStars);
        this.commentEt = (EditText) findViewById(R.id.commentEt);
        this.goodsStars = (RatingBar) findViewById(R.id.goodsStars);
        this.imgll = (LinearLayout) findViewById(R.id.imgll);

        TextView topbarTitle = (TextView) findViewById(R.id.topbar_title);
        if (topbarTitle != null) {
            topbarTitle.setText("评 价");
        }
        if (App.orderItem != null) {
            int count = App.orderItem.getGoodsList().size();
            if (count >= 4) {
                count = 4;
            }
            imgll.removeAllViews();
            for (int i = 0; i < count; i++) {
                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(250, 230);
                layoutParams.setMargins(20, 0, 15, 0);
                imageView.setLayoutParams(layoutParams);
                imageView.setFocusable(false);
                imageView.setFocusableInTouchMode(false);
                App.getPicasso().load(BrnmallAPI.BaseImgUrl1 + App.orderItem.getStoreid()
                        + BrnmallAPI.BaseImgUrl2 + App.orderItem.getGoodsList().get(i).getImg())
                        .placeholder(R.drawable.goodsdefaulimg).error(R.drawable.goodsdefaulimg).into(imageView);
                imgll.addView(imageView);
            }
        }

        btncomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.orderItem = null;
    }


    public void post() {
        if (commentEt.getText().toString().isEmpty()) {
            showToast("输入您的评论吧");
            return;
        }
        String commentText = "";
        String pids = "";
        int goodStars = (int) goodsStars.getRating();
        String stars = "";
        for (int i = 0; i < App.orderItem.getGoodsList().size(); i++) {
            pids += App.orderItem.getGoodsList().get(i).getPid() + "#";
            commentText += commentEt.getText().toString() + "#";
            stars += goodStars + "#";
        }


        int gooddesStars = (int) goodsdesStars.getRating();
        int goodservieStars = (int) serviceStars.getRating();
        int goodbeisStars = (int) beisongStars.getRating();
        UserModel userModel = new Gson().fromJson(App.getPref("LoginResult", ""), UserModel.class);
        String uid = String.valueOf(userModel.getUserInfo().getUid());

        showProgressDialog("正在提交信息");
        BrnmallAPI.ReviewOrder(App.orderItem.getOid(), uid, stars, commentText, pids, gooddesStars + "", goodservieStars + "", goodbeisStars + "", new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                dismissProgressDialog();
                showToast("网络异常,请稍后再试吧");
            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
//                if "false" == json["result"].stringValue {
//                    ProgressHUDManager.showInfoStatus("\(json["data"][0]["msg"].stringValue)")
//                    break
//                }
//                if "true" == json["result"].stringValue {
//                    NSNotificationCenter.defaultCenter().postNotificationName("paySuccessreloadorder", object:nil, userInfo:nil)
//                    ProgressHUDManager.showInfoStatus("\(json["data"][0]["msg"].stringValue)")
//                    self.navigationController ?.popViewControllerAnimated(true)
//
//                }
                if (response != null && !response.isEmpty()) {
                    try {
                        JSONObject json = new JSONObject(response);
                        if ("true".equals(json.getString("result"))) {
                            EventBus.getDefault().post("", "refreshorder");
                            showToast(json.getJSONArray("data").getJSONObject(0).getString("msg"));
                            CommentActivity.this.finish();

                        } else {
                            showToast(json.getJSONArray("data").getJSONObject(0).getString("msg"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    showToast("服务异常,请稍后再试吧");
                }
            }
        });
    }
}
