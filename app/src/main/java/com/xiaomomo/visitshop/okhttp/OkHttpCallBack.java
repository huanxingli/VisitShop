package com.xiaomomo.visitshop.okhttp;

import okhttp3.Request;

/**
 * Created by lihuanxing on 2017/7/16.
 */

public interface OkHttpCallBack {

    public void onSuccess(String response);
    public void onFailure(Request request, Exception e);
}
