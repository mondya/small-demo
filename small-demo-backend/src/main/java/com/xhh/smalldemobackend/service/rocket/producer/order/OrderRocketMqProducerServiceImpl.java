package com.xhh.smalldemobackend.service.rocket.producer.order;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.OrderProducerBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@Slf4j
public class OrderRocketMqProducerServiceImpl implements OrderRocketMqProducerService{
    
    @Resource
    OrderProducerBean orderProducerBean;
    
    @Override
    public void sendMessage(String topic, String tag, String shardingKey, String key, byte[] bytes) {
        Message message = new Message(topic, tag, bytes);
        if (StringUtils.isNotBlank(shardingKey)) {
            // 分区顺序消息，选择分区key，分区key的消息是顺序的
            // 顺序消息选择因子，发送方法基于shardingKey选择具体的消息队列
            message.setShardingKey(shardingKey);
        }
        
        if (StringUtils.isNotBlank(key)) {
            message.setKey(key);
        }
        
        this.sendMessage(message, shardingKey);
    }

    @Override
    public void sendMessage(Message message, String shardingKey) {
        try {
            SendResult send = orderProducerBean.send(message, shardingKey);
            if (Objects.nonNull(send)) {
                log.info("顺序消息发送成功[OrderRocketMqProducerService],topic:{}, tag:{}, key:{}, shardingKey:{}", message.getTopic(), message.getTag(), message.getKey(), message.getShardingKey());
            }
        } catch (Exception e) {
            log.error("顺序消息发送失败[OrderRocketMqProducerService], topic:{}, tag:{}, key:{}, shardingKey:{}, error:", message.getTopic(), message.getTag(), message.getKey(), message.getShardingKey(), e);
        }
    }
}
