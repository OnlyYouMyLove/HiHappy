package com.best.hihappy.mvp.model;

/**
 * Created by FuKaiqiang on 2017-09-06.
 */

public interface NewsInfo<T> {
    void requestData(String type, String key, LoadDataCallBack<T> callBack);
}
