package com.best.hihappy.mvp.model;

import com.best.hihappy.mvp.model.myinterface.LoadDataCallBack;

/**
 * Created by FuKaiqiang on 2017-09-06.
 */

public interface NewsModel<T> {
    void requestData(String type, String key, LoadDataCallBack<T> callBack);
}
