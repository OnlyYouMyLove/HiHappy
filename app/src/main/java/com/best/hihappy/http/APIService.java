package com.best.hihappy.http;


import com.best.hihappy.bean.NewsBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @FormUrlEncoded
    @POST("toutiao/index")
    Observable<NewsBean> getNewsInfo(@Field("type")String type, @Field("key")String key);
}
