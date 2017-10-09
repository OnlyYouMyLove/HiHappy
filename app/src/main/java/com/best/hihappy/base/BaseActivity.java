package com.best.hihappy.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.best.hihappy.R;
import com.best.hihappy.utils.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by FuKaiqiang on 2017-09-10.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        mUnbinder = ButterKnife.bind(this);
        setStatusBarColor();
        initView();
    }

    protected String getIntentData(String s) {
        Intent intent = getIntent();
        if (intent != null) {
            String string = intent.getStringExtra(s);
            return string;
        }
        return null;
    }


    protected void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setStatusBarColor(this, R.color.white);
        }
    }

    protected abstract int getLayoutResource();

    protected abstract void initView();

    protected String getUrl(String url) {
        String s = url;
        return s;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
