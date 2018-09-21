package com.best.hihappy.mvp.view.fragment.main;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
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
    @BindView(R.id.ll_feedback)
    LinearLayout feedback;

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

    @OnClick({R.id.ll_login, R.id.ll_feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                break;
            case R.id.ll_feedback:
                FeedbackAPI.openFeedbackActivity();
                break;
        }
    }
}
