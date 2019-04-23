package com.hybirdflutter.commincation;

import android.app.Activity;
import android.widget.Switch;

import com.hybirdflutter.logs.NTLog;

import java.util.HashMap;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

/**
 * author：yuxinfeng on 2019-04-22 11:18
 * email：yuxinfeng@corp.netease.com
 */
public class HybridChannelBridge implements MethodChannel.MethodCallHandler {

    private Activity activity;

    public static final String CHANNEL = "com.hybrid.demo/plugin";

    public HybridChannelBridge(Activity activity) {
        this.activity = activity;
    }

    public static void registerWith(PluginRegistry.Registrar registrar) {
        MethodChannel channel = new MethodChannel(registrar.messenger(), CHANNEL);
        HybridChannelBridge instance = new HybridChannelBridge(registrar.activity());
        channel.setMethodCallHandler(instance);
    }


    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        String methodName = methodCall.method;
        String tag = "DEMO";
        String log = "";
        Object argument = methodCall.arguments;
        if (argument instanceof HashMap) {
            HashMap aument = (HashMap) methodCall.arguments;
            tag = aument.get("tag") != null ? aument.get("tag").toString() : "DEMO";
            log = aument.get("log").toString();
        }
        switch (methodName) {
            case "log_v":
                NTLog.v(tag, log);
                result.success(true);
                break;
            case "log_w":
                NTLog.v("DEMO", log);
                result.success(true);
                break;
        }

    }
}
