package com.xhh.smalldemobackend.service.rocket.producer.transaction;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.TransactionProducerBean;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionExecuter;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class TransactionRocketMqProducerServiceImpl implements TransactionRocketMqProducerService{
    
    @Resource
    TransactionProducerBean transactionProducerBean;
    
    @Override
    public void sendMessage(Message message) {
        try {
            SendResult sendResult = transactionProducerBean.send(message, new LocalTransactionExecuter() {
                @Override
                public TransactionStatus execute(Message msg, Object arg) {
                    // 执行业务
                    return TransactionStatus.Unknow;
                }
            }, null);
        } catch (Exception e) {
            log.error("[TransactionRocketMqProducerService]事务消息发送失败, topic:{}, tag:{}, key:{}, shardingKey:{}, error:", message.getTopic(), message.getTag(), message.getKey(), message.getShardingKey(), e);
        }
    }

    @Override
    public void sendMessage(String topic, String tag, String key, byte[] bytes) {
        Message message = new Message(topic, tag, bytes);
        if (StringUtils.isNotBlank(key)) {
            message.setKey(key);
        }

        this.sendMessage(message);
    }
}
