package com.xhh.smalldemobackend.service.rocket.consumer.normal;

import com.alibaba.fastjson2.JSON;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.xhh.smalldemobackend.module.dto.DingMsgDTO;
import com.xhh.smalldemobackend.module.dto.WxMsgDTO;
import com.xhh.smalldemobackend.service.message.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class MessageConsumerListener implements MessageListener {
    
    //注入业务service
    @Resource
    private MessageService messageService;
    
    @Override
    public Action consume(Message message, ConsumeContext context) {
        String response = new String(message.getBody());
        String tag = message.getTag();
        try {
            log.info("接收到的topic:{}, message:{}, tag:{}, key:{}", message.getTopic(), message.getBody(), message.getTag(), message.getKey());
            // 业务处理
            switch (tag) {
                case "ding":
                    DingMsgDTO dingMsgDTO = JSON.parseObject(response, DingMsgDTO.class);
                    messageService.sendBaseDingMessage();
                    break;
                case "wx":
                    WxMsgDTO wxMsgDTO = JSON.parseObject(response, WxMsgDTO.class);
                    messageService.sendBaseWxMessage();
                    break;
            }
            return Action.CommitMessage;
        } catch (Exception e){
            log.error("messageConsumerListener 添加失败, e",e);
            return Action.ReconsumeLater;
        }
    }
}
