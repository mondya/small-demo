package com.xhh.smalldemobackend.event.myEvent.msg;

import com.xhh.smalldemobackend.module.dto.DingMsgDTO;


public class DingMsgEvent extends BaseMsgEvent{
    
    private DingMsgDTO dingMsgDTO;
    
    public DingMsgEvent(Object source) {
        super(source);
    }
    
    public DingMsgEvent(Object source, DingMsgDTO dingMsgDTO) {
        super(source);
        this.dingMsgDTO = dingMsgDTO;
    }

    public DingMsgDTO getDingMsgDTO() {
        return dingMsgDTO;
    }

    public void setDingMsgDTO(DingMsgDTO dingMsgDTO) {
        this.dingMsgDTO = dingMsgDTO;
    }
}
