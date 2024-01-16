package com.xhh.smalldemobackend.service.rocket.producer.order;

import com.aliyun.openservices.ons.api.Message;

public interface OrderRocketMqProducerService {
    void sendMessage(String topic, String tag, String shardingKey, String key, byte[] bytes);
    
    void sendMessage(Message message, String shardingKey);
}
