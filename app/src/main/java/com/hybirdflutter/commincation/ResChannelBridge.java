package com.hybirdflutter.commincation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hybirdflutter.logs.NTLog;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import io.flutter.facade.Flutter;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.view.FlutterView;

/**
 * author：yuxinfeng on 2019-04-23 14:37
 * email：yuxinfeng@corp.netease.com
 */
public class ResChannelBridge implements MethodChannel.MethodCallHandler {

    private Context activity;

    public static final String CHANNEL = "com.hybrid.res/plugin";

    public ResChannelBridge(Context activity) {
        this.activity = activity;
    }

    public static void registerWith(PluginRegistry.Registrar registrar) {
        MethodChannel channel = new MethodChannel(registrar.messenger(), CHANNEL);
        HybridChannelBridge instance = new HybridChannelBridge(registrar.activity());
        channel.setMethodCallHandler(instance);
    }

    public static void registerByMessenger(FlutterView view) {
        MethodChannel channel = new MethodChannel(view, CHANNEL);
        ResChannelBridge instance = new ResChannelBridge(view.getContext());
        channel.setMethodCallHandler(instance);
    }


    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        String methodName = methodCall.method;
        String tag = "DEMO";
        String imagename = methodCall.arguments.toString();
        Object argument = methodCall.arguments;

        switch (methodName) {
            case "getNativeDrawable":
                int drawableId = activity.getResources().getIdentifier(imagename, "drawable", activity.getPackageName());
                Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), drawableId);
                byte []bytes = getBytesByBitmap(bitmap);
                result.success(bytes);
                break;
        }
    }

    public byte[] getBytesByBitmap(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bitmap.getByteCount());
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }
}
