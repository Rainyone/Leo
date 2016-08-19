package com.larva.controller.main;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.larva.service.IMonitorService;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;

/**
 * @author sxjun
 * @time 2015/9/2 10:56
 */
@Controller
@RequestMapping("/main/monitor")
public class MonitorController {
    @Resource
    private IMonitorService monitorService;

    //跳转到部门管理
    @RequestMapping("/manage")
    public String monitorManage() {
        return "main/monitor/manage";
    }

    //获取显示的实例
    @RequestMapping(value="/get-list-monitors")
    public
    @ResponseBody
    Pager<Map<String,Object>> getListDeparts(PagerReqVO pagerReqVO) {
    	Pager<Map<String,Object>> vo = monitorService.getPageMonitors(pagerReqVO);
        return vo;
    }

    //获取所有实例树
    @RequestMapping(value="/get-all-monitors")
    public
    @ResponseBody
    ResultVO getAllDeparts() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        ResultVO vo = monitorService.getMonitorTree(Integer.parseInt(principal.toString()));
        return vo;
    }

}
