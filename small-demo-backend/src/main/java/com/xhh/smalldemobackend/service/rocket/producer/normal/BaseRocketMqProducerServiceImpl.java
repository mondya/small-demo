package com.xhh.smalldemobackend.service.rocket.producer.normal;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@Slf4j
public class BaseRocketMqProducerServiceImpl implements BaseRocketMqProducerService {
    
    
    @Resource
    ProducerBean producerBean;
    
    @Override
    public void sendMessage(Message message) {
        try {
            SendResult send = producerBean.send(message);
            if (Objects.nonNull(send)) {
                log.info("[BaseRocketMqProducerService] send success, topic:{}, tag:{}, key:{}, body:{}", message.getTopic(), message.getTag(), message.getKey(), send);
            }
        } catch (Exception e) {
            log.error("[BaseRocketMqProducerService] send failed, e:",e);
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
