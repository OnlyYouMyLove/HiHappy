package com.best.hihappy.mvp.view.fragment.news;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.best.hihappy.R;
import com.best.hihappy.adapter.NewsAdapter;
import com.best.hihappy.base.BaseFragment;
import com.best.hihappy.bean.NewsBean;
import com.best.hihappy.mvp.presenter.NewsPresenterImpl;
import com.best.hihappy.mvp.view.myview.NewsView;
import com.best.hihappy.utils.ToastUtil;
import com.best.hihappy.widget.MyDividerItemDecoration;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by FuKaiqiang on 2017-08-29.
 */

public class JunShiFragment extends BaseFragment implements NewsView {


    @BindView(R.id.top_recyclerview)
    RecyclerView topRecyclerview;
    Unbinder unbinder;
    @BindView(R.id.news_progressbar)
    AVLoadingIndicatorView newsProgressbar;
    Unbinder unbinder1;
    private List<NewsBean.ResultBean.DataBean> mNewsBeanList;
    NewsPresenterImpl mNewsPresenter = new NewsPresenterImpl(this);
    private static final String TAG = "TiYuFragment";

    @Override
    protected void LoadData() {
        Log.e(TAG, "isVisible" + isVisible);
        Log.e(TAG, "isFirst" + isFirst);
        if (isVisible && isFirst) {
            mNewsPresenter.requestData("junshi");
            isFirst = false;
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.news_item_layout;
    }

    @Override
    public void getNewsData(NewsBean newsBean) {
        mNewsBeanList = new ArrayList<>();
        if (mNewsBeanList != null)
            mNewsBeanList.addAll(newsBean.getResult().getData());
        topRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        topRecyclerview.addItemDecoration(new MyDividerItemDecoration(getContext(), MyDividerItemDecoration.VERTICAL_LIST));
        topRecyclerview.setAdapter(new NewsAdapter(mNewsBeanList, getActivity()));
    }

    @Override
    public void showLoading() {
        if (newsProgressbar != null)
            newsProgressbar.show();
    }

    @Override
    public void hideLoading() {
        if (newsProgressbar != null)
            newsProgressbar.hide();
    }

    @Override
    public void showFailedError() {
        ToastUtil.getToast("数据请求失败,请重试").show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
