package com.rj.sarthi.Util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkhttpClient {

    public OkHttpClient getRequestHeader(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.MINUTES)
                .connectTimeout(15, TimeUnit.MINUTES)
                .writeTimeout(30, TimeUnit.MINUTES)
                .build();
        return okHttpClient;
    }
}
