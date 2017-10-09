package com.best.hihappy.mvp.view.myview;

import com.best.hihappy.bean.NewsBean;

/**
 * Created by FuKaiqiang on 2017-09-06.
 */

public interface NewsView {

    void showLoading();

    void hideLoading();

    void showFailedError();

    void getNewsData(NewsBean newsBean);

}
