package com.best.hihappy.mvp.view.fragment.main;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.best.hihappy.R;
import com.best.hihappy.adapter.NewsFragmentPagerAdapter;
import com.best.hihappy.base.BaseFragment;
import com.best.hihappy.mvp.view.fragment.news.CaiJingFragment;
import com.best.hihappy.mvp.view.fragment.news.GuoJiFragment;
import com.best.hihappy.mvp.view.fragment.news.GuoNeiFragment;
import com.best.hihappy.mvp.view.fragment.news.JunShiFragment;
import com.best.hihappy.mvp.view.fragment.news.KeJiFragment;
import com.best.hihappy.mvp.view.fragment.news.ShiShangFragment;
import com.best.hihappy.mvp.view.fragment.news.TiYuFragment;
import com.best.hihappy.mvp.view.fragment.news.TopFragment;
import com.best.hihappy.mvp.view.fragment.news.YuLeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by FuKaiqiang on 2017-06-12.
 */

public class NewsFragment extends BaseFragment {


    @BindView(R.id.news_tablayout)
    XTabLayout newsTablayout;
    @BindView(R.id.news_viewpager)
    ViewPager newsViewpager;
    private List<Fragment> mFragmentList;
    private List<String> mTitles;

    @Override
    public int getLayoutResource() {
        return R.layout.newsfragment_layout;
    }

    @Override
    protected void LoadData() {}

    protected void initView() {

        mFragmentList = new ArrayList<>();
        mFragmentList.add(new TopFragment());
        mFragmentList.add(new YuLeFragment());
        mFragmentList.add(new TiYuFragment());
        mFragmentList.add(new KeJiFragment());
        mFragmentList.add(new JunShiFragment());
        mFragmentList.add(new GuoNeiFragment());
        mFragmentList.add(new GuoJiFragment());
        mFragmentList.add(new CaiJingFragment());
        mFragmentList.add(new ShiShangFragment());

        mTitles = new ArrayList<>();
        mTitles.add("头条");
        mTitles.add("娱乐");
        mTitles.add("体育");
        mTitles.add("科技");
        mTitles.add("军事");
        mTitles.add("国内");
        mTitles.add("国际");
        mTitles.add("财经");
        mTitles.add("时尚");

        newsViewpager.setAdapter(new NewsFragmentPagerAdapter(getChildFragmentManager(), mFragmentList, mTitles));
        newsTablayout.setupWithViewPager(newsViewpager);

    }

}
