package com.best.hihappy.mvp.view.activity;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.best.hihappy.R;
import com.best.hihappy.adapter.MainFragmentPagerAdapter;
import com.best.hihappy.base.BaseActivity;
import com.best.hihappy.mvp.view.fragment.main.LiveFragment;
import com.best.hihappy.mvp.view.fragment.main.MyCenterFragment;
import com.best.hihappy.mvp.view.fragment.main.NewsFragment;
import com.best.hihappy.mvp.view.fragment.main.VideoFragment;
import com.best.hihappy.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by FuKaiqiang on 2017-08-28.
 */

public class MainActivity extends BaseActivity {


    @BindView(R.id.main_tablayout)
    TabLayout mainTablayout;
    @BindView(R.id.main_viewpager)
    NoScrollViewPager mainViewpager;

    private List<Fragment> mFragmentList;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setStatusBarColor();
        setAdapterAndsetTagLayout();
    }

    private void setAdapterAndsetTagLayout() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new NewsFragment());
        mFragmentList.add(new VideoFragment());
        mFragmentList.add(new LiveFragment());
        mFragmentList.add(new MyCenterFragment());

        mainViewpager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList));
        mainTablayout.setupWithViewPager(mainViewpager);
        mainTablayout.getTabAt(0).setCustomView(R.layout.tab_item_news);
        mainTablayout.getTabAt(1).setCustomView(R.layout.tab_item_video);
        mainTablayout.getTabAt(2).setCustomView(R.layout.tab_item_live);
        mainTablayout.getTabAt(3).setCustomView(R.layout.tab_item_mycenter);
    }

}

