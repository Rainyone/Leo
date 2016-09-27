package com.larva.controller.main;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.larva.service.IAppManageService;
import com.larva.service.IOrderService;
import com.larva.vo.OrderVo;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.TreeNode;

@Controller
@RequestMapping("/main/tongji")
public class TongjiController {
	@Autowired
	private IOrderService orderService;
	//跳转到app管理
    @RequestMapping("/order/manage")
    public String tongjiManage() {
        return "main/tongji/order/manage";
    }
    //获取所有APP
    @RequestMapping("/order/query")
    public
    @ResponseBody
    Pager<Map<String,Object>> getOrderList(PagerReqVO pagerReqVO,OrderVo orderVo,HttpSession session) {
    	Pager<Map<String,Object>> vo = orderService.getOrderList(pagerReqVO,orderVo);
        return vo;
    }
}
