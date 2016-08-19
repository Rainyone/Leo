package com.larva.webSocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.larva.mq.listener.HeartBeatListener;
import com.larva.utils.Constants;

public class TextMessageHandler extends TextWebSocketHandler {
	
	private static final Logger log = LoggerFactory.getLogger(TextWebSocketHandler.class);

    private static final Map<String, WebSocketSession> users;

    static {
        users = new HashMap<String, WebSocketSession>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        /*
         * 链接成功后会触发此方法，可在此处对离线消息什么的进行处理
         */
        Map<String, Object> attributes = session.getAttributes();
        if (null == attributes || attributes.isEmpty()) {
            log.debug("没有参数....");
        } else {
        	for (Map.Entry<String, Object> entry : attributes.entrySet()) {  
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
                if(Constants.DEFAULT_WEBSOCKET_HOSTNAME.equals(entry.getKey()))
                	on(entry.getValue().toString());
            }
        	users.put(session.getId(), session);
            String username = (String) attributes.get(Constants.DEFAULT_WEBSOCKET_USERNAME);
            log.debug("{}:connect success...",username);
            session.sendMessage(new TextMessage("{'ok':true}"));
            
        }
       
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        /*
         * 前端 websocket.send() 会触发此方法 
         */
    	log.debug("message -> " + message.getPayload());
        super.handleTextMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
        	 Map<String, Object> attributes = session.getAttributes();
             if (null == attributes || attributes.isEmpty()) {
                 log.debug("没有参数....");
             } else {
             	for (Map.Entry<String, Object> entry : attributes.entrySet()) {  
                     System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
                     if(Constants.DEFAULT_WEBSOCKET_HOSTNAME.equals(entry.getKey()))
                     	off(entry.getValue().toString());
                 }
             }
            session.close();
        }
        log.error(exception.getMessage());
        log.debug("websocket connection closed......");
        users.remove(session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("websocket connection closed......");
        Map<String, Object> attributes = session.getAttributes();
        if (null == attributes || attributes.isEmpty()) {
            log.debug("没有参数....");
        } else {
        	for (Map.Entry<String, Object> entry : attributes.entrySet()) {  
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
                if(Constants.DEFAULT_WEBSOCKET_HOSTNAME.equals(entry.getKey()))
                	off(entry.getValue().toString());
            }
        }
        users.remove(session.getId());
    }

    /**
     * 发送点对点消息
     * @param username
     * @param message
     */
    public void sendMessageToUser(String username, TextMessage message) {
        Iterator<Map.Entry<String, WebSocketSession>> it = userIterator();
        while (it.hasNext()) {
            WebSocketSession session = it.next().getValue();
            if (username.equals(session.getAttributes().get(Constants.DEFAULT_WEBSOCKET_USERNAME))) {
                try {
                    if (session.isOpen())
                        session.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 广播消息
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        Iterator<Map.Entry<String, WebSocketSession>> it = userIterator();
        while (it.hasNext()) {
            WebSocketSession session = it.next().getValue();
            try {
                if (session.isOpen())
                    session.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private Iterator<Map.Entry<String, WebSocketSession>> userIterator() {
        Set<Map.Entry<String, WebSocketSession>> entrys = users.entrySet();
        return entrys.iterator();
    }
    
    private void on(String hostname){
    	if(HeartBeatListener.isON.containsKey(hostname)){
    		HeartBeatListener.isON.put(hostname,true);
    	}
    }
    
    private void off(String hostname){
    	if(HeartBeatListener.isON.containsKey(hostname)){
    		HeartBeatListener.isON.put(hostname,false);
    	}
    }
}
