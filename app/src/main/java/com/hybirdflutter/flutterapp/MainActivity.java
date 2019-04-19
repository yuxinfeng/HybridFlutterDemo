package com.hybirdflutter.flutterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hybirdflutter.commincation.BasicChannelManager;
import com.hybirdflutter.commincation.EventChannelManager;
import com.hybirdflutter.commincation.MethodChannelManager;

import io.flutter.facade.Flutter;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.common.StandardMethodCodec;
import io.flutter.view.FlutterView;

import static com.hybirdflutter.commincation.ChannelConst.ANDROID_AND_FLUTTER_CHANNEL;
import static com.hybirdflutter.commincation.ChannelConst.ANDROID_EVENT_CHANNEL;
import static com.hybirdflutter.commincation.ChannelConst.ANDROID_METHOD_CHANNEL;
import static com.hybirdflutter.commincation.ChannelConst.INIT_ROUTE;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private BasicMessageChannel<Object> basicMessageChannel;
    private MethodChannel methodChannel;
    private EventChannel eventChannel;
    private EventChannel.EventSink meventSink;
    FlutterView view;
    FrameLayout flutterLayout;
    TextView basicSend;
    TextView methodSend;
    TextView eventSend;
    TextView resActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initflutterView();
        createChannel(view);
        initChannel();
    }

    private void createChannel(FlutterView view) {
        MethodChannelManager.getInstance().createMethodChannel(view, ANDROID_METHOD_CHANNEL, StandardMethodCodec.INSTANCE);
        BasicChannelManager.getInstance().createBasicChannel(view, ANDROID_AND_FLUTTER_CHANNEL, StandardMessageCodec.INSTANCE);
        EventChannelManager.getInstance().createEventChannel(view, ANDROID_EVENT_CHANNEL, StandardMethodCodec.INSTANCE);
    }

    private void initChannel() {
        basicMessageChannel = BasicChannelManager.getInstance().getBasicChannelByChannelName(ANDROID_AND_FLUTTER_CHANNEL);
        //dart主动发送，等待平台方回复
        basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler<Object>() {
            @Override
            public void onMessage(Object s, BasicMessageChannel.Reply<Object> reply) {
                Log.e(TAG, "basicMessageChannel===" + s);
                Toast.makeText(getBaseContext(), (String) s, Toast.LENGTH_SHORT).show();
            }
        });

        methodChannel = MethodChannelManager.getInstance().getMethodChannelByChannelName(ANDROID_METHOD_CHANNEL);
        methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
                String method = methodCall.method;
                Object arguments = methodCall.arguments;
                switch (method) {
                    case "method1":
                        String replay = "invoke native method1";
                        result.success(replay);

                        Toast.makeText(getBaseContext(), arguments.toString(), Toast.LENGTH_SHORT).show();
                        break;
                    case "method2":
                        boolean arg = false;
                        if (arguments instanceof Boolean) {
                            arg = (Boolean) arguments;
                        }
                        String replay2 = "invoke native method2" + arg;
                        Toast.makeText(getBaseContext(), replay2, Toast.LENGTH_SHORT).show();
                        result.success(replay2);
                        break;
                }

            }
        });


        eventChannel = EventChannelManager.getInstance().getEventChannelByChannelName(ANDROID_EVENT_CHANNEL);
        eventChannel.setStreamHandler(new EventChannel.StreamHandler() {
            @Override
            public void onListen(Object o, EventChannel.EventSink eventSink) {
                meventSink = eventSink;
            }

            @Override
            public void onCancel(Object o) {

            }
        });
    }

    private void initView() {
        flutterLayout = findViewById(R.id.flutterview);
        basicSend = findViewById(R.id.basicchannel_send);
        methodSend = findViewById(R.id.methodchannel_send);
        eventSend = findViewById(R.id.eventchannel_send);
        resActivity = findViewById(R.id.tv_resActivity);
        basicSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //native主动发起，（platform发送到达dart, 等待dart回复）
                basicMessageChannel.send("native发出的字符串", new BasicMessageChannel.Reply<Object>() {
                    @Override
                    public void reply(Object s) {
                        Log.e(TAG, "basicMessageChannel===" + s);
                        Toast.makeText(getBaseContext(), (String) s, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        methodSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                methodChannel.invokeMethod("flutter_method1", null);
            }
        });

        eventSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meventSink.success("I am from native 端 event Channel");
            }
        });

        resActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ResActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initflutterView() {
        // 获取 Flutter view
        view = Flutter.createView(MainActivity.this, getLifecycle(), INIT_ROUTE);
        // 创建 LayoutParams
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        // 添加 flutterView 到当前 Activity
        flutterLayout.addView(view, layoutParams);
    }

}
