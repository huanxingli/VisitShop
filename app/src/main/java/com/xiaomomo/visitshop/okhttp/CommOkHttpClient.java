package com.xiaomomo.visitshop.okhttp;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lihuanxing on 2017/7/16.
 */

public class CommOkHttpClient {

    private static final int TIME_OUT = 30;
    private OkHttpClient client;
    private static CommOkHttpClient instance;
    private Handler handler;

    private CommOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //设置超时时间
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);

        //支持重定向
        builder.followRedirects(true);

        client = builder.build();

        handler = new Handler(Looper.getMainLooper());
    }


    public static CommOkHttpClient getInstance() {
        if (instance == null) {
            synchronized (CommOkHttpClient.class) {
                if (instance == null) {
                    instance = new CommOkHttpClient();
                }
            }
        }
        return instance;
    }

    /**
     * 封装post请求
     * @param url
     * @param params
     * @param callback
     */
    public void postRequest(String url, HashMap<String,String> params, final OkHttpCallBack callback){
        final Request request = new OkHttpRequest().postRequest(url,params);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(request, e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String str = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(str);

                    }
                });
            }
        });
    }

    /**
     * 封装get请求
     * @param url
     * @param callBack
     */
    public void getRequest(String url, final OkHttpCallBack callBack){
        final Request request = new OkHttpRequest().getRequest(url);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(request,e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(str);
                    }
                });
            }
        });
    }
}
