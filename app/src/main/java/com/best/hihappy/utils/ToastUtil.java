package com.best.hihappy.utils;

import android.widget.Toast;

import com.best.hihappy.constant.MyApplication;


/**
 * Created by FuKaiqiang on 2017-08-29.
 */

public class ToastUtil {

    private volatile static Toast mToast;

    private ToastUtil() {
    }

    public static Toast getToast(String message) {
        if (mToast == null) {
            synchronized (ToastUtil.class) {
                if (mToast == null) {
                    mToast = Toast.makeText(MyApplication.getContext(), message, Toast.LENGTH_SHORT);
                }
            }
        }else{
            mToast.setText(message);
        }
        return mToast;
    }

}
