package com.best.hihappy.base;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.best.hihappy.R;
import com.best.hihappy.utils.StatusBarUtil;
import com.best.hihappy.widget.ActivityCollector;
import com.best.hihappy.widget.NetworkChangeReceiver;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by FuKaiqiang on 2017-09-10.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;
    private NetworkChangeReceiver networkChangeReceiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        mUnbinder = ButterKnife.bind(this);
        setStatusBarColor();
        initView();
        addActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetWorkListener();
    }

    /**
     * 添加Activity到集合统一管理
     */
    private void addActivity() {
        ActivityCollector.addActivity(this);
    }


    /**
     * 通过广播，监听网络变化
     */
    private void registerNetWorkListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
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
    protected void onPause() {
        super.onPause();
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
            networkChangeReceiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        ActivityCollector.removeActivity(this);
    }
}
