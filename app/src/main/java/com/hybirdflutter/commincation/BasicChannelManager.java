package com.hybirdflutter.commincation;

import java.util.HashMap;

import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;

/**
 * author：yuxinfeng on 2019-04-19 15:03
 * email：yuxinfeng@corp.netease.com
 */
public class BasicChannelManager {

    private HashMap<String, BasicMessageChannel> basicChannelList = new HashMap<>();

    private static class SingletonHolder {
        static final BasicChannelManager INSTANCE = new BasicChannelManager();
    }

    public static BasicChannelManager getInstance() {
        return BasicChannelManager.SingletonHolder.INSTANCE;
    }

    public BasicMessageChannel createBasicChannel(BinaryMessenger messenger, String channel, MessageCodec codec)  {
        if (messenger == null || channel ==  null) {
            throw new RuntimeException("Method Channel 参数不合法");
        }
        if (codec == null) {
            codec = StandardMessageCodec.INSTANCE;
        }

        if (basicChannelList.containsKey(channel)) {
            return basicChannelList.get(channel);
        } else {
            BasicMessageChannel methodChannel =  new BasicMessageChannel(messenger, channel, codec);
            basicChannelList.put(channel, methodChannel);
            return methodChannel;
        }
    }

    public BasicMessageChannel getBasicChannelByChannelName(String name) {
        return basicChannelList.get(name);
    }

}
