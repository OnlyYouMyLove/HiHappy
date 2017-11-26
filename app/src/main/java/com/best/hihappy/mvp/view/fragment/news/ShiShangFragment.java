package com.best.hihappy.mvp.view.fragment.news;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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


/**
 * Created by FuKaiqiang on 2017-08-29.
 */

public class ShiShangFragment extends BaseFragment implements NewsView, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.top_recyclerview)
    RecyclerView newsRecyclerview;
    @BindView(R.id.news_progressbar)
    AVLoadingIndicatorView newsProgressbar;
    @BindView(R.id.top_refresh)
    SwipeRefreshLayout newsRefresh;

    private List<NewsBean.ResultBean.DataBean> mNewsBeanList = new ArrayList<>();
    private List<NewsBean.ResultBean.DataBean> mNewDatas;

    NewsPresenterImpl mNewsPresenter = new NewsPresenterImpl(this);
    private static final String TAG = "TiYuFragment";

    @Override
    protected void LoadData() {
        if (isVisible && isFirst) {
            mNewsPresenter.requestData("shishang");
            isFirst = false;
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.news_item_layout;
    }


    @Override
    protected void initView() {
        initSwipeRefresh();
    }

    private void initSwipeRefresh() {
        newsRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        newsRefresh.setOnRefreshListener(this);
    }

    @Override
    public void getNewsData(NewsBean newsBean) {
        initData(newsBean);
        initRecyclerView();
    }

    private void initData(NewsBean newsBean) {
        List<NewsBean.ResultBean.DataBean> dataBeanList = newsBean.getResult().getData();
        for (int i = 0; i < dataBeanList.size(); i++) {

        }
        if (mNewsBeanList != null) mNewsBeanList.addAll(newsBean.getResult().getData());
    }

    private void initRecyclerView() {
        mManager = new LinearLayoutManager(getActivity());
        newsRecyclerview.setLayoutManager(mManager);
        newsRecyclerview.addItemDecoration(new MyDividerItemDecoration(getContext(), MyDividerItemDecoration.VERTICAL_LIST));
        mAdapter = new NewsAdapter(getDatas(0, PAGE_COUNT),
                getActivity(), getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
        newsRecyclerview.setAdapter(mAdapter);
        newsRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mAdapter.isFadeTips() == false && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(LOADMORE, mAdapter.getRealLastPosition(), mAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (mAdapter.isFadeTips() == true && lastVisibleItem + 1 == mAdapter.getItemCount() + 1) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(LOADMORE, mAdapter.getRealLastPosition(), mAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mManager.findLastVisibleItemPosition();
            }
        });
    }

    private List<NewsBean.ResultBean.DataBean> getDatas(final int firstIndex, final int lastIndex) {
        List<NewsBean.ResultBean.DataBean> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < mNewsBeanList.size()) {
                resList.add(mNewsBeanList.get(i));
            }
        }
        return resList;
    }

    private void updateRecyclerView(int loadrefreshOrLaodmore, int fromIndex, int toIndex) {
        mNewDatas = getDatas(fromIndex, toIndex);
        if (mNewDatas.size() > 0) {
            mAdapter.updateList(loadrefreshOrLaodmore, mNewDatas, true);
        } else {
            mAdapter.updateList(loadrefreshOrLaodmore, null, false);
        }
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
    public void onRefresh() {
        newsRefresh.setRefreshing(true);
        if (mNewsBeanList.size() == 0) {
            mNewsPresenter.requestData("top");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    newsRefresh.setRefreshing(false);
                }
            }, 1000);
        } else {
            mAdapter.resetDatas();
            updateRecyclerView(LOADREFRESH, 0, PAGE_COUNT);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    newsRefresh.setRefreshing(false);
                }
            }, 1000);
        }
    }
}
