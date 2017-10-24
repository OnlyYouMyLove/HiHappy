package com.best.hihappy.mvp.view.fragment.main;

import android.content.Intent;
import android.widget.LinearLayout;

import com.best.hihappy.R;
import com.best.hihappy.base.BaseFragment;
import com.best.hihappy.mvp.view.activity.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by FuKaiqiang on 2017-08-29.
 */

public class MyCenterFragment extends BaseFragment {


    @BindView(R.id.ll_login)
    LinearLayout llLogin;

    @Override
    protected int getLayoutResource() {
        return R.layout.mycenterfragment_layout;
    }

    @Override
    protected void LoadData() {

    }

    @Override
    protected void initView() {


    }

    @OnClick(R.id.ll_login)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }
}
