package com.xhh.smalldemobackend.event.listen;

import com.xhh.smalldemobackend.event.myEvent.MyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyListenerB {
    
    @EventListener
    public void onApplicationEvent(MyEvent event) {
        System.out.println("listenerB");
    }
}
