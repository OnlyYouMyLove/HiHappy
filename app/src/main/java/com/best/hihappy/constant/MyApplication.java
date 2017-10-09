package com.best.hihappy.constant;

import android.app.Application;
import android.content.Context;

import com.best.hihappy.sonic.SonicRuntimeImpl;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;

/**
 * Created by FuKaiqiang on 2017-08-25.
 */

public class MyApplication extends Application {

    private static Context mContext;
    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initContext();
        initLeakCanary();
        initSonic();
    }

    private void initSonic() {
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(this), new SonicConfig.Builder().build());
        }
    }

    private void initContext() {
        mContext = getApplicationContext();
    }

    protected void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    //单例模式获取上下文
    public static Context getContext() {
        return mContext;
    }
}
