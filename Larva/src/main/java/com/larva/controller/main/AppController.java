package com.larva.controller.main;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.larva.service.IAppManageService;
import com.larva.utils.Constants;
import com.larva.utils.DESEncrypt;
import com.larva.vo.AppManageCreateVO;
import com.larva.vo.AppManageEditVO;
import com.larva.vo.GetURLVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;

@Controller
@RequestMapping("/main/app")
public class AppController {
	Logger logger = Logger.getLogger(AppController.class);
	@Autowired
	private IAppManageService appManageService;
	//跳转到app管理
    @RequestMapping("/manage")
    public String departmentManage() {
        return "main/app/manage";
    }
    //获取所有APP
    @RequestMapping("/get-list-apps")
    public
    @ResponseBody
    Pager<Map<String,Object>> getListApps(PagerReqVO pagerReqVO,TreeNode tree) {
    	Pager<Map<String,Object>> vo = appManageService.getAppManages(pagerReqVO);
        return vo;
    }
    //获取所有区域信息
    @RequestMapping("/get-all-areas")
    public
    @ResponseBody
    ResultVO getListAreas() {
    	ResultVO vo = appManageService.getAreaTree();
        return vo;
    }
    
    //创建APP
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO createAppManage(@Valid @ModelAttribute AppManageCreateVO createVO, BindingResult bindingResult, HttpSession session, HttpServletRequest request) {
        ResultVO resultVO = new ResultVO(true);
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }
        createVO.setCreate_user_name((String)session.getAttribute(Constants.DEFAULT_SESSION_USERNAME));
        createVO.setUpdate_user_name((String)session.getAttribute(Constants.DEFAULT_SESSION_USERNAME));
        resultVO = appManageService.saveAppManage(createVO);
        return resultVO;
    }
    
    //编辑APP
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO editAppManage(@Valid @ModelAttribute AppManageEditVO createVO, BindingResult bindingResult,  HttpSession session) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ResultVO resultVO = new ResultVO(true);

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }
        createVO.setUpdate_user_name((String)session.getAttribute(Constants.DEFAULT_SESSION_USERNAME));
        resultVO = appManageService.editAppManage(createVO);
        return resultVO;
    }
    
    //删除APP
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO delAppManage(@RequestParam("appIds[]") String[] appIds, HttpSession session) {
        ResultVO resultVO = appManageService.deleteAppManage(appIds,(String)session.getAttribute(Constants.DEFAULT_SESSION_USERNAME));
        return resultVO;
    }
    
    //设置新的Key
    @RequestMapping(value = "/setNewKey", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO setNewKey( String app_key,String app_id, HttpSession session) {
        ResultVO resultVO = appManageService.updateNewKey(app_key,(String)session.getAttribute(Constants.DEFAULT_SESSION_USERNAME),app_id);
        return resultVO;
    }
    //跳转到isp设置
    @RequestMapping("/app_rule/isp/manage")
    public String appIspSet() {
        return "main/app_rule/isp/manage";
    }
    //跳转到app区域设置
    @RequestMapping("/app_rule/area/manage")
    public String appAreaSet() {
        return "main/app_rule/area/manage";
    }
  //跳转到app_code设置
    @RequestMapping("/app_rule/app_code/manage")
    public String appCodeSet() {
        return "main/app_rule/app_code/manage";
    }
    //获取运营商限制信息
    @RequestMapping("/app_rule/isp/get-list")
    @ResponseBody
    public Pager<Map<String,Object>> getListChargeIsps(PagerReqVO pagerReqVO) {
    	Pager<Map<String,Object>> vo = appManageService.getAreaIsps(pagerReqVO);
        return vo;
    }
  //新增运营商限制信息
    @RequestMapping("/app_rule/isp/create")
    @ResponseBody
    public ResultVO createAppIsps(String app_id,String isp_id,int isChecked,HttpSession session) {
    	String update_people_name = (String)session.getAttribute(Constants.DEFAULT_SESSION_USERNAME);
    	ResultVO vo= appManageService.createAppIsps(app_id,isp_id,isChecked,update_people_name);
        return vo;
    }
    @RequestMapping("/app_rule/isp/dels")
    @ResponseBody
    public ResultVO delAppIsps(String app_id,HttpSession session) {
    	String update_people_name = (String)session.getAttribute(Constants.DEFAULT_SESSION_USERNAME);
    	ResultVO vo= appManageService.delAppIsps(app_id,update_people_name);
        return vo;
    }
  //获取适用区域
    @RequestMapping("/app_rule/area/get-list-area")
    @ResponseBody
    public ResultVO getListAppArea(String app_id) {
    	ResultVO vo = appManageService.getListAppArea(app_id); 
        return vo;
    }
  //设置单个适用区域
	  @RequestMapping("/app_rule/area/set-one-area")
	  @ResponseBody
	  public ResultVO setOneAppArea(String id,String app_id,String area_id,int checked,int rxl,int yxl) {
	  	ResultVO vo = appManageService.createOneAppArea(id,area_id,app_id,checked,rxl,yxl); 
	    return vo;
	  }
	//获取适用计费代码列表
    @RequestMapping("/app_rule/app_code/get-list")
    @ResponseBody
    public Pager<Map<String,Object>> getListAppCodes(PagerReqVO pagerReqVO,String app_id) {
    	Pager<Map<String,Object>> vo = appManageService.getListAppCodes(pagerReqVO,app_id);
        return vo;
    }
  //获取适用计费代码列表
    @RequestMapping("/app_rule/get-list-charge-codes")
    @ResponseBody
    public Pager<Map<String,Object>> getListChargeCodes(PagerReqVO pagerReqVO,String app_id) {
    	Pager<Map<String,Object>> vo = appManageService.getListChargeCodes(pagerReqVO,app_id);
        return vo;
    }
  //app新增计费代码限制信息
    @RequestMapping("/app_rule/app_code/create")
    @ResponseBody
    public ResultVO createAppCodes(String charge_id,String app_id,int isChecked,HttpSession session) {
    	String update_people_name = (String)session.getAttribute(Constants.DEFAULT_SESSION_USERNAME);
    	ResultVO vo= appManageService.createAppCodes(charge_id,app_id,isChecked,update_people_name);
        return vo;
    }
    //删除计费代
    @RequestMapping(value = "/app_rule/app_code/del", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO delAppCodes(@RequestParam("appCodeRuleIds[]") String[] appCodeRuleIds,HttpSession session, HttpServletRequest request) {
        ResultVO resultVO = appManageService.deleteappCodes(appCodeRuleIds,(String)session.getAttribute(Constants.DEFAULT_SESSION_USERNAME));
        return resultVO;
    }
    //跳转到app_test设置
    @RequestMapping("/app_test/manage")
    public String appTest() {
        return "main/app/app_test/manage";
    }
    
    @RequestMapping(value = "/app_test/get-url", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO delAppCodes(GetURLVO v) {
    	Long timestamp = new Date().getTime();
    	String url = "setCharge?app_id=" + v.getApp_id();
    	url += "&app_key=" + v.getApp_key();
    	url += "&request_type=" + v.getRequest_type();
    	url += "&channel=" + v.getChannel();
    	url += "&price=" + v.getPrice();
    	url += "&imei=" + v.getImei();
    	url += "&imsi=" + v.getImsi();
    	url += "&bsc_lac=" + v.getBsc_lac();
    	url += "&bsc_cid=" + v.getBsc_cid();
    	url += "&mobile=" + v.getMobile();
    	url += "&iccid=" + v.getIccid();
    	url += "&mac=" + v.getMac();
    	url += "&cpparm=" + v.getCpparm();
    	url += "&fmt=" + v.getFmt();
    	url += "&timestamp=" + timestamp;
    	url += "&isp=" + v.getIsp();
    	url += "&code_id=" + v.getCode_id();
    	url += "&order_id=" + v.getOrder_id();
    	url += "&ver_code=" + v.getVer_code();
    	logger.info("oldUrl = " + url);
    	String newUrl = DESEncrypt.getDesUrl(url);
    	String sign = DESEncrypt.getSign(v.getApp_id(), v.getApp_key(), v.getImei(), String.valueOf(timestamp));
    	logger.info("newUrl = " + newUrl);
    	logger.info("sign = " + sign);
    	ResultVO vo = new ResultVO(true);
    	String result = "http://ip:port/Larva-inf/charge?body=" + newUrl +"&sign=" + sign ; 
    	vo.setData(result);
        return vo;
    }
}