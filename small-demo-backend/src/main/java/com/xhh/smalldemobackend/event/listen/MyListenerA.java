package com.xhh.smalldemobackend.event.listen;

import com.xhh.smalldemobackend.event.myEvent.MyEvent;
import com.xhh.smalldemobackend.mapper.UserMapper;
import com.xhh.smalldemocommon.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyListenerA implements ApplicationListener<MyEvent> {
    
    @Resource
    private UserMapper userMapper;
    
    @Override
    public void onApplicationEvent(MyEvent event) {
        System.out.println("listenerA");
        if (StringUtils.isNotBlank(event.getName())) {
            User user = new User();
            user.setName(event.getName());
            user.setAge(11);
            user.setEmail("event@gmail.com");
            user.setStatus((byte) 1);
            userMapper.insert(user);
        }
    }
}
