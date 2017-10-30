package com.best.hihappy.mvp.view.fragment.news;

import android.os.Handler;
import android.os.Looper;
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

public class TopFragment extends BaseFragment implements NewsView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.top_recyclerview)
    RecyclerView topRecyclerview;
    @BindView(R.id.news_progressbar)
    AVLoadingIndicatorView newsProgressbar;
    @BindView(R.id.top_refresh)
    SwipeRefreshLayout topRefresh;

    private List<NewsBean.ResultBean.DataBean> mNewsBeanList;
    NewsPresenterImpl mNewsPresenter = new NewsPresenterImpl(this);
    private static final String TAG = "TopFragment";
    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 10;
    private LinearLayoutManager mManager;
    private NewsAdapter mAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private List<NewsBean.ResultBean.DataBean> mNewDatas;
    private static int LOADREFRESH = 1;
    private static int LOADMORE = 2;

    @Override
    protected void LoadData() {
        Log.e(TAG, "isVisible" + isVisible);
        Log.e(TAG, "isFirst" + isFirst);
        if (isVisible && isFirst) {
            mNewsPresenter.requestData("top");
            isFirst = false;
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.news_item_layout;
    }

    @Override
    public void getNewsData(NewsBean newsBean) {
        initData(newsBean);
        initRecyclerView();
        initSwipeRefresh();
    }

    private void initData(NewsBean newsBean) {
        mNewsBeanList = new ArrayList<>();
        if (mNewsBeanList != null) mNewsBeanList.addAll(newsBean.getResult().getData());
    }

    private void initSwipeRefresh() {
        topRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        topRefresh.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        mManager = new LinearLayoutManager(getActivity());
        topRecyclerview.setLayoutManager(mManager);
        topRecyclerview.addItemDecoration(new MyDividerItemDecoration(getContext(), MyDividerItemDecoration.VERTICAL_LIST));
        mAdapter = new NewsAdapter(getDatas(0, PAGE_COUNT),
                getActivity(), getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
        topRecyclerview.setAdapter(mAdapter);
        topRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.e(TAG,"lastVisibleItem::"+lastVisibleItem);
                    Log.e(TAG,"adapter.getItemCount()::"+mAdapter.getItemCount());
                    Log.e(TAG,"mAdapter.getRealLastPosition()::"+mAdapter.getRealLastPosition());
                    if (mAdapter.isFadeTips() == false && lastVisibleItem+1  == mAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.e(TAG,"我执行了吗01");
                                updateRecyclerView(LOADMORE, mAdapter.getRealLastPosition(), mAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (mAdapter.isFadeTips() == true && lastVisibleItem + 1 == mAdapter.getItemCount()+1) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.e(TAG,"我执行了吗02");
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
        topRefresh.setRefreshing(true);
        mAdapter.resetDatas();
        updateRecyclerView(LOADREFRESH, 0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                topRefresh.setRefreshing(false);
            }
        }, 1000);
    }
}
