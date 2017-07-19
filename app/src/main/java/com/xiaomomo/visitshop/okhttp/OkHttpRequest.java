package com.xiaomomo.visitshop.okhttp;

import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by lihuanxing on 2017/7/16.
 * 封装OkHttp的Request
 */

public class OkHttpRequest {

    /**
     * 创建post请求的request
     * @param url
     * @param hashMap
     * @return
     */
    public Request postRequest(String url, HashMap<String,String> hashMap){
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (hashMap != null){
            for (String key : hashMap.keySet()){
                formBuilder.add(key,hashMap.get(key));
            }
        }
        FormBody body = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
