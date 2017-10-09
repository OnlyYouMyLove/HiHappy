package com.best.hihappy.mvp.presenter.impl;

import com.best.hihappy.constant.Constants;
import com.best.hihappy.mvp.model.NewsModel;
import com.best.hihappy.bean.NewsBean;
import com.best.hihappy.mvp.model.impl.NewsInfo;
import com.best.hihappy.mvp.model.myinterface.LoadDataCallBack;
import com.best.hihappy.mvp.presenter.NewsPresenter;
import com.best.hihappy.mvp.view.myview.NewsView;

/**
 * Created by FuKaiqiang on 2017-09-06.
 */

public class NewsPresenterImpl implements NewsPresenter {

    private NewsView mNewsView;
    private NewsModel mNewsModel;

    public NewsPresenterImpl(NewsView newsView) {
        mNewsView = newsView;
        mNewsModel = new NewsInfo();
    }

    @Override
    public void requestData(String type) {
        mNewsView.showLoading();
        mNewsModel.requestData(type, Constants.KEY, new LoadDataCallBack<NewsBean>() {
            @Override
            public void onSuccess(NewsBean newsBean) {
                mNewsView.hideLoading();
                mNewsView.getNewsData(newsBean);
            }

            @Override
            public void onFailed() {
                mNewsView.showFailedError();
            }

        });
    }
}
