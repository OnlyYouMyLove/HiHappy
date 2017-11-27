package com.best.hihappy.mvp.view.fragment.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.best.hihappy.R;
import com.best.hihappy.adapter.LiveFragmentAdapter;
import com.best.hihappy.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by FuKaiqiang on 2017-06-12.
 */

public class VideoFragment extends BaseFragment {

    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    private List<String> mList = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.videofragment_layout;
    }

    @Override
    protected void initView() {
        mList.add("Hello");
        mList.add("World");
        mList.add("Hello");
        mList.add("World");
        mList.add("Hello");
        mList.add("World");
        mList.add("Hello");
        mList.add("World");
        rvVideo.setLayoutManager(new LinearLayoutManager(getContext()));
        LiveFragmentAdapter adapter = new LiveFragmentAdapter(mList);
        rvVideo.setAdapter(adapter);
    }

    @Override
    protected void LoadData() {

    }
}
