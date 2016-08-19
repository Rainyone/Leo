package com.larva.webSocket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.larva.utils.Constants;
import com.larva.utils.StrKit;

public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	private static final Logger log = LoggerFactory.getLogger(WebSocketHandshakeInterceptor.class);

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        log.debug("After Handshake..............");
        super.afterHandshake(request, response, wsHandler, ex);
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes)
            throws Exception {
        log.debug("Before Handshake..............");
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                String username = (String) session.getAttribute(Constants.DEFAULT_SESSION_USERNAME);
                if (username == null)
                    username = "system";
                attributes.put(Constants.DEFAULT_WEBSOCKET_USERNAME, username);
            }
            String query = servletRequest.getURI().getQuery();
            if(StrKit.notBlank(query)){
            	String[] querys = query.split("&");
            	for(String q:querys){
            		String[] qs = q.split("=");
            		if(qs.length==2&&Constants.DEFAULT_WEBSOCKET_HOSTNAME.equals(qs[0])){
            			attributes.put(Constants.DEFAULT_WEBSOCKET_HOSTNAME, qs[1]);
            		}
            	}
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

}
