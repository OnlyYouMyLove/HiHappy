package com.best.hihappy.utils;

import android.widget.ProgressBar;

import com.best.hihappy.base.BaseFragment;

/**
 * Created by FuKaiqiang on 2017-09-06.
 */

public class ProgressBarUtil {

    private volatile static ProgressBar mSprogressbar;

    private ProgressBarUtil() {
    }

    public static ProgressBar showProgressBar() {
        if (mSprogressbar == null) {
            synchronized (BaseFragment.class) {
                if (mSprogressbar == null) {
//                    mSprogressbar = new ProgressBar();
                }
            }
        }
        return mSprogressbar;
    }
}
