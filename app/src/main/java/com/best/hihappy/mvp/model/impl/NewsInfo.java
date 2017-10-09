package com.best.hihappy.mvp.model.impl;


import com.best.hihappy.mvp.model.NewsModel;
import com.best.hihappy.bean.NewsBean;
import com.best.hihappy.http.APIRetrofit;
import com.best.hihappy.mvp.model.myinterface.LoadDataCallBack;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by FuKaiqiang on 2017-09-06.
 */

public class NewsInfo implements NewsModel<NewsBean> {

    @Override
    public void requestData(String type, String key, final LoadDataCallBack<NewsBean> loadDataCallBack) {
        APIRetrofit.getAPIService().getNewsInfo(type, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull NewsBean newsBeen) {
                        loadDataCallBack.onSuccess(newsBeen);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        loadDataCallBack.onFailed();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
