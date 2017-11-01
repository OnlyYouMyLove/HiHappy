package com.best.hihappy.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.best.hihappy.utils.NetworkUtil;

/**
 * Created by FuKaiqiang on 2017-11-01.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        if (!NetworkUtil.checkNetworkConnect(context)) {
            Toast.makeText(context,"没有网络",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("温馨提示");
            alertDialog.setMessage("请检查网络设置");
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent =  new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                    context.startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("退出程序", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialog.show();
        }
    }
}
