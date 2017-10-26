package com.best.hihappy.mvp.view.fragment.news;

import android.os.Handler;
import android.os.Looper;
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
import com.best.hihappy.utils.getNewsDataUtil;
import com.best.hihappy.utils.updateRecyUtil;
import com.best.hihappy.widget.MyDividerItemDecoration;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by FuKaiqiang on 2017-08-29.
 */

public class TopFragment extends BaseFragment implements NewsView {

    @BindView(R.id.top_recyclerview)
    RecyclerView topRecyclerview;
    @BindView(R.id.news_progressbar)
    AVLoadingIndicatorView newsProgressbar;

    private List<NewsBean.ResultBean.DataBean> mNewsBeanList;
    NewsPresenterImpl mNewsPresenter = new NewsPresenterImpl(this);
    private static final String TAG = "TopFragment";
    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 10;
    private LinearLayoutManager mManager;
    private NewsAdapter mAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());

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
        mNewsBeanList = new ArrayList<>();
        if (mNewsBeanList != null) mNewsBeanList.addAll(newsBean.getResult().getData());
        initRecyclerView();
    }

    private void initRecyclerView() {
        mManager = new LinearLayoutManager(getActivity());
        topRecyclerview.setLayoutManager(mManager);
        topRecyclerview.addItemDecoration(new MyDividerItemDecoration(getContext(), MyDividerItemDecoration.VERTICAL_LIST));
        mAdapter = new NewsAdapter(getNewsDataUtil.getDatas(0, PAGE_COUNT, mNewsBeanList),
                getActivity(), getNewsDataUtil.getDatas(0, PAGE_COUNT, mNewsBeanList).size() > 0 ? true : false);
        topRecyclerview.setAdapter(mAdapter);
        topRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mAdapter.isFadeTips() == false && lastVisibleItem +1== mAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyUtil.updateRecyclerView(mAdapter.getRealLastPosition(), mAdapter.getRealLastPosition() + PAGE_COUNT, mNewsBeanList, mAdapter);
                                Log.e(TAG,"执行了吗01");
                            }
                        }, 500);
                    }

                    if (mAdapter.isFadeTips() == true && lastVisibleItem + 2 == mAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyUtil.updateRecyclerView(mAdapter.getRealLastPosition(), mAdapter.getRealLastPosition() + PAGE_COUNT, mNewsBeanList, mAdapter);
                                Log.e(TAG,"执行了吗02");
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mManager.findLastVisibleItemPosition();
                Log.e(TAG,"lastVisibleItem"+lastVisibleItem);
                Log.e(TAG,"mAdapter.getItemCount()"+mAdapter.getItemCount());
            }
        });
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
}
