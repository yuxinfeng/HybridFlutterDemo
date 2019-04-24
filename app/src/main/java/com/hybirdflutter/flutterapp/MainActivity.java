package com.hybirdflutter.flutterapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hybirdflutter.commincation.BasicChannelManager;
import com.hybirdflutter.commincation.EventChannelManager;
import com.hybirdflutter.commincation.MethodChannelManager;
import com.hybirdflutter.network.NetWorkActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.flutter.facade.Flutter;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
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
    PluginRegistry.Registrar registrar = null ;

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


        if(!view.getPluginRegistry().hasPlugin(ANDROID_METHOD_CHANNEL) ) {
            registrar = view.getPluginRegistry().registrarFor(ANDROID_METHOD_CHANNEL);
        }
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
        findViewById(R.id.img_de).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HyBirdActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.imgs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String key = view.getLookupKeyForAsset("assets/images/weizhang.png");
               AssetManager assetManager;
               assetManager = registrar.context().getAssets();
               try {
                    InputStream in = assetManager.open(key);
                    byte[] data = getBytes(in);
                    if (data != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        ((ImageView)findViewById(R.id.imgs)).setImageBitmap(bitmap);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.tv_netActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NetWorkActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
     * 得到图片字节流数组大小  inputStream  --> byte
     */
    public static byte[] getBytes(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
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
