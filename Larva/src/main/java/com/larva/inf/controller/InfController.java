package com.larva.inf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/chat")
public class InfController {
	
	@RequestMapping(value="/charge", method=RequestMethod.GET)
	@ResponseBody
    public String login() {
        return "test";
    }
}