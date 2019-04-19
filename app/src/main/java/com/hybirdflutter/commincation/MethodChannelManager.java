package com.hybirdflutter.commincation;

import java.util.HashMap;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodCodec;
import io.flutter.plugin.common.StandardMethodCodec;

/**
 * author：yuxinfeng on 2019-04-19 14:37
 * email：yuxinfeng@corp.netease.com
 */
public class MethodChannelManager {

    private HashMap<String, MethodChannel> methodChannelList = new HashMap<>();

    private static class SingletonHolder {
        static final MethodChannelManager INSTANCE = new MethodChannelManager();
    }

    public static MethodChannelManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public MethodChannel createMethodChannel(BinaryMessenger messenger, String channel, MethodCodec codec)  {
        if (messenger == null || channel ==  null) {
            throw new RuntimeException("Method Channel 参数不合法");
        }
        if (codec == null) {
            codec = StandardMethodCodec.INSTANCE;
        }

        if (methodChannelList.containsKey(channel)) {
            return methodChannelList.get(channel);
        } else {
            MethodChannel methodChannel =  new MethodChannel(messenger, channel, codec);
            methodChannelList.put(channel, methodChannel);
            return methodChannel;
        }
    }

    public MethodChannel getMethodChannelByChannelName(String name) {
        return methodChannelList.get(name);
    }

}
