package com.best.hihappy.http;


import com.best.hihappy.constant.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIRetrofit {

    /**
     * 默认超时时间 单位/秒
     */
    private static final int DEFAULT_TIME_OUT = 20;

    private volatile static Retrofit sRetrofit = null;

    private volatile static OkHttpClient sOKHttpClient = null;

    private APIRetrofit() {
    }

    public static APIService getAPIService() {
        return getInstance().create(APIService.class);
    }

    public static Retrofit getInstance() {
        if (sRetrofit == null) {
            synchronized (APIRetrofit.class) {
                if (sRetrofit == null) {
                    sRetrofit = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(getsOKHttpClient())
                            .build();

                }
            }
        }
        return sRetrofit;
    }

    public static OkHttpClient getsOKHttpClient() {
        if (sOKHttpClient == null) {
            synchronized (APIRetrofit.class) {
                if (sOKHttpClient == null) {
                    sOKHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return sOKHttpClient;
    }


}
