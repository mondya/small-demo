package com.xhh.smalldemobackend.event.myEvent;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;


public class MyEvent extends ApplicationEvent {
    
    private String name;
    
    public MyEvent(Object source) {
        super(source);
    }

    public MyEvent(Object source, Clock clock) {
        super(source, clock);
    }

    public MyEvent(Object source,  String name) {
        super(source);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
