package com.xhh.smalldemobackend.event.listen.msg;

import com.alibaba.fastjson2.JSON;
import com.xhh.smalldemobackend.event.myEvent.msg.BaseMsgEvent;
import com.xhh.smalldemobackend.event.myEvent.msg.DingMsgEvent;
import com.xhh.smalldemobackend.event.myEvent.msg.WxMsgEvent;
import com.xhh.smalldemobackend.module.dto.DingMsgDTO;
import com.xhh.smalldemobackend.module.dto.WxMsgDTO;
import com.xhh.smalldemobackend.service.rocket.producer.normal.BaseRocketMqProducerService;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

public class MsgEventListener implements ApplicationListener<BaseMsgEvent> {

    public static final String WX_SIMPLE_NAME = "WxMsgEvent";
    public static final String DING_SIMPLE_NAME = "DingMsgEvent";
    
    @Resource
    private BaseRocketMqProducerService baseRocketMqProducerService;

    @Override
    public void onApplicationEvent(@NotNull BaseMsgEvent event) {
        String simpleName = event.getClass().getSimpleName();
        switch (simpleName) {
            case WX_SIMPLE_NAME:
                WxMsgEvent wxMsgEvent = (WxMsgEvent) event;
                WxMsgDTO wxMsgDTO = wxMsgEvent.getWxMsgDTO();
                // sendWx
                baseRocketMqProducerService.sendMessage("tp", "wx", null, JSON.toJSONString(wxMsgDTO).getBytes(StandardCharsets.UTF_8));
                break;
            case DING_SIMPLE_NAME:
                DingMsgEvent dingMsgEvent = (DingMsgEvent) event;
                DingMsgDTO dingMsgDTO = dingMsgEvent.getDingMsgDTO();
                baseRocketMqProducerService.sendMessage("tp", "ding", null, JSON.toJSONString(dingMsgDTO).getBytes(StandardCharsets.UTF_8));
                // sendDing
                break;
        }
    }
}
