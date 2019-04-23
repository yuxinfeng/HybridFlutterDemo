package com.hybirdflutter;

import android.app.Application;

import com.hybirdflutter.logs.NTLog;

/**
 * author：yuxinfeng on 2019-04-22 16:55
 * email：yuxinfeng@corp.netease.com
 */
public class HybirdApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NTLog.init(this);
    }
}
