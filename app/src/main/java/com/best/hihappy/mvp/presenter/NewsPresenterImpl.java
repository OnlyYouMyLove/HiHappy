package com.best.hihappy.mvp.presenter;

import com.best.hihappy.bean.NewsBean;
import com.best.hihappy.constant.Constants;
import com.best.hihappy.mvp.model.NewsInfo;
import com.best.hihappy.mvp.model.NewsInfoImpl;
import com.best.hihappy.mvp.model.LoadDataCallBack;
import com.best.hihappy.mvp.view.myview.NewsView;

/**
 * Created by FuKaiqiang on 2017-09-06.
 */

public class NewsPresenterImpl implements NewsPresenter {

    private NewsView mNewsView;
    private NewsInfo mNewsModel;

    public NewsPresenterImpl(NewsView newsView) {
        mNewsView = newsView;
        mNewsModel = new NewsInfoImpl();
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
                mNewsView.hideLoading();
                mNewsView.showFailedError();
            }

        });
    }
}
