package com.xhh.smalldemobackend.service.rocket.producer.normal;

import com.aliyun.openservices.ons.api.Message;

public interface BaseRocketMqProducerService {
    void sendMessage(Message message);
    
    void sendMessage(String topic, String tag, String key, byte[] bytes);
}
