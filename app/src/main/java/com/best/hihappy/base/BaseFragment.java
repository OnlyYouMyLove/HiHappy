package com.best.hihappy.base;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.best.hihappy.adapter.NewsAdapter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by FuKaiqiang on 2017-08-30.
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;
    protected boolean isVisible = false;
    protected boolean isFirst = true;
    protected View mView;
    protected Handler mHandler = new Handler(Looper.getMainLooper());

    protected final int PAGE_COUNT = 10;
    protected static int LOADMORE = 2;
    protected int lastVisibleItem = 0;
    protected static int LOADREFRESH = 1;

    protected LinearLayoutManager mManager;
    protected NewsAdapter mAdapter;


    //根据Fragment的生命周期，先回调setUserVisibleHint，再回调onCreateView，所以先看setUserVisibleHint方法
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            LoadData();
        }
    }

    //看完BaseFragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) mView = inflater.inflate(getLayoutResource(), container, false);
        unbinder = ButterKnife.bind(this, mView);
        initView();
        return mView;
    }

    protected abstract int getLayoutResource();

    protected abstract void LoadData();

    protected void initView() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
