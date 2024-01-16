package com.xhh.smalldemobackend.event.myEvent.msg;

import org.springframework.context.ApplicationEvent;

public abstract class BaseMsgEvent extends ApplicationEvent {

    public BaseMsgEvent(Object source) {
        super(source);
    }
}
