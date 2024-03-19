package com.xhh.smalldemobackend.websocket;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xhh.smalldemobackend.config.GetHttpSessionConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfig.class)
@Component
@Slf4j
public class ChatEndpoint {
    
    private static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();
    
    private HttpSession httpSession;

    /**
     * 建立websocket连接后调用
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        // 将session进行保存
        this.httpSession = (HttpSession) endpointConfig.getUserProperties().get(HttpSession.class.getName());
        // 登录时需要把用户id保存到session中
        String userId = (String) this.httpSession.getAttribute("userId");
        sessionMap.put(userId, session);
        
        // 广播消息，需要将登录的所有的用户推送给所有用户
        broad2AllUsers(String.format("%s is online", userId));
    }
    
    
    private void broad2AllUsers(String message) {
        // 遍历sessionMap，将消息发送给所有用户
        for (Map.Entry<String, Session> entry : sessionMap.entrySet()) {
            Session session = entry.getValue();
            try {
                session.getBasicRemote().sendText(message); 
            }catch (Exception e) {
                log.error("发送消息失败", e);
            }
        }
    }

    /**
     * 浏览器发送消息
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        JSONObject jsonObject = JSON.parseObject(message);
        String toUserId = jsonObject.getString("toUserId");
        String content = jsonObject.getString("content");

        // 获取接收方的session对象
        Session session = sessionMap.get(toUserId);
        
        try {
            session.getBasicRemote().sendText(content);
        } catch (Exception e) {
            log.error("发送消息失败", e);
        }
    }

    /**
     * 断开websocket调用
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        // 移除当前登录的用户
        String userId = (String) this.httpSession.getAttribute("userId");
        sessionMap.remove(userId);
        // 广播消息，需要将登录的所有的用户推送给所有用户
        broad2AllUsers(String.format("%s is offline", userId));
    }
    
    
}
