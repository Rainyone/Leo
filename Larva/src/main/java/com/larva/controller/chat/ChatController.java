package com.larva.controller.chat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.larva.utils.Constants;
import com.larva.webSocket.TextMessageHandler;

@Controller
@RequestMapping("/chat")
public class ChatController {
	
   /* @RequestMapping("login")
    public String login() {
        return "chat/login";
    }

    @RequestMapping("submit")
    public String submit(HttpServletRequest request,HttpSession session, @RequestParam("username") String username) {
        session.setAttribute(Constants.DEFAULT_SESSION_USERNAME, username);
        return "redirect:index";
    }*/

    @RequestMapping("index")
    public String index() {
        return "chat/index";
    }
    
    @Bean
    public TextMessageHandler textMessageHandler() {
        return new TextMessageHandler();
    }

    @RequestMapping("message")
    public String view() {
        return "chat/message";
    }

    @RequestMapping("send")
    public String send(HttpServletRequest request, @RequestParam("username") String username) {
        TextMessage message = new TextMessage(request.getParameter("message"));
        textMessageHandler().sendMessageToUser(username, message);
        return "chat/message";
    }
}
