package com.hybirdflutter.commincation;

import java.util.HashMap;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCodec;
import io.flutter.plugin.common.StandardMethodCodec;

/**
 * author：yuxinfeng on 2019-04-19 15:03
 * email：yuxinfeng@corp.netease.com
 */
public class EventChannelManager {
    private HashMap<String, EventChannel> eventChannelList = new HashMap<>();

    private static class SingletonHolder {
        static final EventChannelManager INSTANCE = new EventChannelManager();
    }

    public static EventChannelManager getInstance() {
        return EventChannelManager.SingletonHolder.INSTANCE;
    }

    public EventChannel createEventChannel(BinaryMessenger messenger, String channel, MethodCodec codec)  {
        if (messenger == null || channel ==  null) {
            throw new RuntimeException("Method Channel 参数不合法");
        }
        if (codec == null) {
            codec = StandardMethodCodec.INSTANCE;
        }

        if (eventChannelList.containsKey(channel)) {
            return eventChannelList.get(channel);
        } else {
            EventChannel methodChannel =  new EventChannel(messenger, channel, codec);
            eventChannelList.put(channel, methodChannel);
            return methodChannel;
        }
    }

    public EventChannel getEventChannelByChannelName(String name) {
        return eventChannelList.get(name);
    }
}
