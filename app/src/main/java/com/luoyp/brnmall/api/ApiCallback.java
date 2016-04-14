package com.luoyp.brnmall.api;

import com.luoyp.xlibrary.net.OkHttpClientManager;
import com.squareup.okhttp.Request;

/**
 * Created by lyp3314@gmail.com on 16/4/14.
 */
public abstract class ApiCallback<T> extends OkHttpClientManager.ResultCallback<T> {

    @Override
    public void onBefore(Request request) {
        super.onBefore(request);
    }

    @Override
    public void onAfter() {
        super.onAfter();
    }
}