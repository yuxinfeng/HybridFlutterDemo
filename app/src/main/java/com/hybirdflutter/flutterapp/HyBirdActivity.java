package com.hybirdflutter.flutterapp;

import android.os.Bundle;

import com.hybirdflutter.commincation.HybridChannelBridge;

import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;

import static com.hybirdflutter.commincation.ChannelConst.Hybird_ROUTE;

/**
 * author：yuxinfeng on 2019-04-22 10:44
 * email：yuxinfeng@corp.netease.com
 */
public class HyBirdActivity extends FlutterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);
        HybridChannelBridge.registerWith(this.registrarFor(HybridChannelBridge.CHANNEL));
        getFlutterView().setInitialRoute(Hybird_ROUTE);
    }
}
