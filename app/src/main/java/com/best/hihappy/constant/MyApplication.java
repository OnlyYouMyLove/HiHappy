package com.best.hihappy.constant;

import android.app.Application;
import android.content.Context;

import com.best.hihappy.sonic.SonicRuntimeImpl;
import com.mob.MobSDK;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

/**
 * Created by FuKaiqiang on 2017-08-25.
 */

public class MyApplication extends Application {

    private static Context mContext;
//    private RefWatcher refWatcher;

//    public static RefWatcher getRefWatcher(Context context) {
//        MyApplication application = (MyApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        initShareSdk();
        initContext();
//        initLeakCanary();
        initSonic();
        initLitePal();
        createDataBase();
    }

    /**
     * 创建数据库
     */
    private void createDataBase() {
        Connector.getDatabase();
    }

    private void initLitePal() {
        LitePal.initialize(this);
    }

    private void initShareSdk() {
        MobSDK.init(this, "217ca689007e7", "268d5f53de72600b3212d6d639f5f5e6");
    }

    private void initSonic() {
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(this), new SonicConfig.Builder().build());
        }
    }

    private void initContext() {
        mContext = getApplicationContext();
    }

//    protected void initLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        refWatcher = LeakCanary.install(this);
//    }

    //单例模式获取上下文
    public static Context getContext() {
        return mContext;
    }
}
