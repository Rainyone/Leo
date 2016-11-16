package com.larva.controller.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.larva.service.IAppManageService;
import com.larva.service.IOrderService;
import com.larva.utils.JSONUtil;
import com.larva.vo.OrderVo;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.ResultVOTest;
import com.larva.vo.TestUser;
import com.larva.vo.TreeNode;
import com.mini.core.Record;

@Controller
@RequestMapping("/main/tongji")
public class TongjiController {
	@Autowired
	private IOrderService orderService;
	
    @RequestMapping("/order/manage")
    public String tongjiManage() {
        return "main/tongji/order/manage";
    }
    @RequestMapping("/platform/manage")
    public String platform() {
        return "main/tongji/platform/manage";
    }
    @RequestMapping("/app/manage")
    public String app() {
        return "main/tongji/app/manage";
    }
    @RequestMapping("/charge/manage")
    public String charge() {
        return "main/tongji/charge/manage";
    }
    @RequestMapping("/order/query")
    public
    @ResponseBody
    Pager<Map<String,Object>> getOrderList(PagerReqVO pagerReqVO,OrderVo orderVo,HttpSession session) {
    	Pager<Map<String,Object>> vo = orderService.getOrderList(pagerReqVO,orderVo);
        return vo;
    }
    @RequestMapping("/order/appLog")
    public
    @ResponseBody
    Pager<Map<String,Object>> getAppLog(PagerReqVO pagerReqVO,String charge_key,HttpSession session) {
    	Pager<Map<String,Object>> vo = orderService.getAppLog(pagerReqVO,charge_key);
        return vo;
    }
    @RequestMapping("/platform/query")
    public
    @ResponseBody
    Pager<Map<String,Object>> getPlatformQuery(PagerReqVO pagerReqVO,String datetimeStart,String datetimeEnd,HttpSession session) {
    	Pager<Map<String,Object>> vo = orderService.getPlatformQuery(pagerReqVO,datetimeStart,datetimeEnd);
        return vo;
    }
    @RequestMapping("/platform/queryCount")
    public
    @ResponseBody
    ResultVO getPlatformQueryCount(PagerReqVO pagerReqVO,String datetimeStart,String datetimeEnd,HttpSession session) {
    	ResultVO vo = new ResultVO(true);
    	Map<String, Object>  m = orderService.getPlatformQueryCount();
    	vo.setData(m);
        return vo;
    }
    @RequestMapping("/app/queryAppAll")
    public
    @ResponseBody
    ResultVO queryAppAll(PagerReqVO pagerReqVO,String datetimeStart,String datetimeEnd,HttpSession session) {
    	ResultVO vo = new ResultVO(true);
    	List <Map<String,Object>> list = orderService.getAppAllList();
    	vo.setData(list);
    	return vo;
    }
    @RequestMapping("/app/queryCount")
    public
    @ResponseBody
    ResultVO getAppQueryCount(String datetimeStart,String datetimeEnd,String app_id,HttpSession session) {
    	ResultVO vo = new ResultVO(true);
    	Map<String, Object>  m = orderService.getAppQueryCount(app_id);
    	vo.setData(m);
        return vo;
    }
    @RequestMapping("/app/query")
    public
    @ResponseBody
    Pager<Map<String,Object>> getAppQuery(PagerReqVO pagerReqVO,String datetimeStart,String datetimeEnd,String app_id,HttpSession session) {
    	Pager<Map<String,Object>> vo = orderService.getAppQuery(pagerReqVO,datetimeStart,datetimeEnd,app_id);
        return vo;
    }
    @RequestMapping("/app/charts")
    public
    @ResponseBody
    ResultVO getAppCharts(String datetimeStart,String datetimeEnd,String app_id,HttpSession session) {
    	ResultVO vo = new ResultVO(true);
    	Map<String, Object>  m = orderService.getAppCharts(datetimeStart,datetimeEnd,app_id);
    	vo.setData(m);
        return vo;
    }
    @RequestMapping("/charge/queryAppAll")
    public
    @ResponseBody
    ResultVO queryChargeAll(PagerReqVO pagerReqVO,String datetimeStart,String datetimeEnd,HttpSession session) {
    	ResultVO vo = new ResultVO(true);
    	List <Map<String,Object>> list = orderService.getChargeAllList();
    	vo.setData(list);
    	return vo;
    }
    @RequestMapping("/charge/queryCount")
    public
    @ResponseBody
    ResultVO getChargeQueryCount(String datetimeStart,String datetimeEnd,String charge_id,HttpSession session) {
    	ResultVO vo = new ResultVO(true);
    	Map<String, Object>  m = orderService.getChargeQueryCount(charge_id);
    	vo.setData(m);
        return vo;
    }
    @RequestMapping("/charge/query")
    public
    @ResponseBody
    Pager<Map<String,Object>> getChargeQuery(PagerReqVO pagerReqVO,String datetimeStart,String datetimeEnd,String charge_id,HttpSession session) {
    	Pager<Map<String,Object>> vo = orderService.getChargeQuery(pagerReqVO,datetimeStart,datetimeEnd,charge_id);
        return vo;
    }
    @RequestMapping("/charge/charts")
    public
    @ResponseBody
    ResultVO getChargeCharts(String datetimeStart,String datetimeEnd,String charge_id,HttpSession session) {
    	ResultVO vo = new ResultVO(true);
    	Map<String, Object>  m = orderService.getChargeCharts(datetimeStart,datetimeEnd,charge_id);
    	vo.setData(m);
        return vo;
    }
    @RequestMapping("/platform/charts")
    public
    @ResponseBody
    ResultVO getPlatformCharts(String datetimeStart,String datetimeEnd,HttpSession session) {
    	ResultVO vo = new ResultVO(true);
    	Map<String, Object>  m = orderService.getPlatformCharts(datetimeStart,datetimeEnd);
    	vo.setData(m);
        return vo;
    }
}
