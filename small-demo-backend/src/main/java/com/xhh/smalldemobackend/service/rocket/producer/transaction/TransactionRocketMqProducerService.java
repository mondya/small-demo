package com.xhh.smalldemobackend.service.rocket.producer.transaction;

import com.aliyun.openservices.ons.api.Message;

public interface TransactionRocketMqProducerService {
    void sendMessage(Message message);

    void sendMessage(String topic, String tag, String key, byte[] bytes);
}
