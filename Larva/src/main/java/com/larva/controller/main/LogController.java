package com.larva.controller.main;

import com.larva.service.ILogService;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import javax.annotation.Resource;

/**
 * Created by sxjun on 15-9-12.
 */
@Controller
@RequestMapping("/main/log")
public class LogController {
    @Resource
    private ILogService logService;

    //跳转到登录日志页面
    @RequestMapping("/login")
    public String loginLog() {
        return "main/log/login";
    }

    //查询登录日志
    @RequestMapping(value = "/query-login")
    public
    @ResponseBody
    Pager<Map<String,Object>> queryLoginLog(PagerReqVO pagerReqVO) {
    	Pager<Map<String,Object>> vo = logService.queryLoginLog(pagerReqVO);
        return vo;
    }
   /* @RequestMapping(value = "/query-login", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO queryLoginLog(int page, int rows) {
        ResultVO resultVO = logService.queryLoginLog(page, rows);
        return resultVO;
    }*/

    //用户分布图
    @RequestMapping("/user-locations")
    public String userLocations() {
        return "main/log/user-locations";
    }


    /**
     * 获取用户坐标
     *
     * @return
     */
    @RequestMapping("/getLocations")
    public
    @ResponseBody
    ResultVO getLocations() {
        return logService.getAllUserLocations();
    }


}
