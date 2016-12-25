package com.larva.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.larva.service.TestService;
import com.larva.vo.ResultVO;

@Controller
@RequestMapping("/test")
public class TestController {
	@Resource
	private TestService testService;
    @RequestMapping(value = "/test-update")
    public
    @ResponseBody
    ResultVO testUpdate() {
        testService.aupdateTest();;
        return new ResultVO();
    }
}
