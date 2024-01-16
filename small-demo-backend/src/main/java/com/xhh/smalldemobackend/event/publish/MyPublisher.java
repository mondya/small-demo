package com.xhh.smalldemobackend.event.publish;

import com.xhh.smalldemobackend.event.myEvent.MyEvent;
import com.xhh.smalldemobackend.event.myEvent.msg.DingMsgEvent;
import com.xhh.smalldemobackend.event.myEvent.msg.WxMsgEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyPublisher implements ApplicationContextAware {
    
    private ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 发布事件
     * 监听该事件的监听者可以获取到消息
     * @param event
     */
    public void publishEvent(MyEvent event) {
        log.info("发布事件");
        applicationContext.publishEvent(event);
    }
    
    
    public void publishWxMsgEvent(WxMsgEvent wxMsgEvent) {
        log.info("[wxMsg]发布消息事件");
        applicationContext.publishEvent(wxMsgEvent);
    }

    public void publishDingMsgEvent(DingMsgEvent dingMsgEvent) {
        log.info("[dingMsg]发布消息事件");
        applicationContext.publishEvent(dingMsgEvent);
    }
}
