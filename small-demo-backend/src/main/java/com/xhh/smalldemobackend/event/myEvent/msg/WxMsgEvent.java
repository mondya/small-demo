package com.xhh.smalldemobackend.event.myEvent.msg;

import com.xhh.smalldemobackend.module.dto.WxMsgDTO;

public class WxMsgEvent extends BaseMsgEvent{
    
    private WxMsgDTO wxMsgDTO;
    
    public WxMsgEvent(Object source) {
        super(source);
    }
    
    public WxMsgEvent(Object source, WxMsgDTO wxMsgDTO) {
        super(source);
        this.wxMsgDTO = wxMsgDTO;
    }

    public WxMsgDTO getWxMsgDTO() {
        return wxMsgDTO;
    }

    public void setWxMsgDTO(WxMsgDTO wxMsgDTO) {
        this.wxMsgDTO = wxMsgDTO;
    }
}
