package com.sohu110.airapp;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sohu110.airapp.log.Logger;

import im.fir.sdk.FIR;


/**
 * Created by Aaron on 2016/4/6.
 */
public class LibApplication extends Application {

    private final String TAG = this.getClass().getSimpleName();

    private static LibApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        FIR.init(this);
        initialize();
        instance = this;
    }

    private void initialize() {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo info = pm.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
//            if(info != null) {
//                appVersion = info.versionName;
//                appVersionCode = info.versionCode;
//            }
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * 获取当前Application实例
     */
    public static LibApplication getInstance() {
        return instance;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }
}
