package com.best.hihappy.mvp.model.myinterface;

/**
 * Created by FuKaiqiang on 2017-09-06.
 */

public interface LoadDataCallBack<T> {
    void onSuccess(T t);
    void onFailed();
}
