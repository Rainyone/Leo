package com.larva.controller.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
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
import com.larva.service.IChargeCodeRuleService;
import com.larva.service.IChargeCodeService;
import com.larva.utils.Constants;
import com.larva.vo.AppManageCreateVO;
import com.larva.vo.AreaChargeCodeListVO;
import com.larva.vo.AreaChargeCodeVO;
import com.larva.vo.ChargeCodeCreateVO;
import com.larva.vo.ChargeCodeDisableTimeCreateVo;
import com.larva.vo.ChargeCodeEditVO;
import com.larva.vo.MenuCreateVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;

@Controller
@RequestMapping("/main/charge_code_rule")
public class ChargeCodeRuleController {
	@Autowired
	private IChargeCodeRuleService chargeCodeRuleService;
	//跳转到计费代码规则管理
    @RequestMapping("/disableTime/manage")
    public String chargeCodeManage() {
        return "main/charge_code_rule/disable_time/manage";
    }
    //获取失效时间记录
    @RequestMapping("/disableTime/get-list-disabled-times")
    @ResponseBody
    public Pager<Map<String,Object>> getListChargeDisabledTimes(PagerReqVO pagerReqVO,String chargeCodeId) {
    	Pager<Map<String,Object>> vo = chargeCodeRuleService.getChargeCodeDisabledTimes(pagerReqVO,chargeCodeId);
        return vo;
    }
    //创建计费代码失效时间
    @RequestMapping(value = "/disableTime/create", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO createChargeCode(@Valid @ModelAttribute ChargeCodeDisableTimeCreateVo createVO,BindingResult bindingResult, HttpSession session, HttpServletRequest request) {
        ResultVO resultVO = new ResultVO(true);
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }
        createVO.setCreate_people_name((String)session.getAttribute(Constants.DEFAULT_SESSION_USERNAME));
        resultVO = chargeCodeRuleService.saveChargeCodeDisableTime(createVO);
        return resultVO;
    }
    //删除计费代码失效时间
    @RequestMapping(value = "/disableTime/del", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO delChargeCodes(@RequestParam("chargeCodeDisableTimeIds[]") String[] chargeCodeDisableTimeIds,HttpSession session, HttpServletRequest request) {
        ResultVO resultVO = chargeCodeRuleService.deleteChargeCodeDisableTimes(chargeCodeDisableTimeIds,(String)session.getAttribute(Constants.DEFAULT_SESSION_USERNAME));
        return resultVO;
    }
  //跳转到计费代码规则管理--运营商设置
    @RequestMapping("/isp/manage")
    public String chargeCodeISP() {
        return "main/charge_code_rule/isp/manage";
    }

    //获取运营商限制信息
    @RequestMapping("/isp/get-list")
    @ResponseBody
    public Pager<Map<String,Object>> getListChargeIsps(PagerReqVO pagerReqVO) {
    	Pager<Map<String,Object>> vo = chargeCodeRuleService.getChargeCodeIsps(pagerReqVO);
        return vo;
    }
  //新建运营商限制信息
    @RequestMapping("/isp/create")
    @ResponseBody
    public ResultVO createChargeIsps(String code_id,String isp_id,int isChecked,HttpSession session) {
    	String update_people_name = (String)session.getAttribute(Constants.DEFAULT_SESSION_USERNAME);
    	ResultVO vo= chargeCodeRuleService.createChargeIsps(code_id,isp_id,isChecked,update_people_name);
        return vo;
    }
  //删除运营商限制信息
    @RequestMapping("/isp/dels")
    @ResponseBody
    public ResultVO delChargeIsps(String code_id,HttpSession session) {
    	String update_people_name = (String)session.getAttribute(Constants.DEFAULT_SESSION_USERNAME);
    	ResultVO vo= chargeCodeRuleService.delChargeIsps(code_id,update_people_name);
        return vo;
    }
  //跳转到计费代码规则管理--区域
    @RequestMapping("/area/manage")
    public String chargeCodeArea() {
        return "main/charge_code_rule/area/manage";
    }
  //获取适用区域
    @RequestMapping("/area/get-list-area")
    @ResponseBody
    public ResultVO getListChargeArea(String code_id) {
    	ResultVO vo = chargeCodeRuleService.getListChargeArea(code_id); 
        return vo;
    }
    //设置单个适用区域
	  @RequestMapping("/area/set-one-area")
	  @ResponseBody
	  public ResultVO setOneChargeArea(String id,String charge_code_id,String area_id,int checked,int rxl,int yxl) {
	  	ResultVO vo = chargeCodeRuleService.createOneChargeArea(id,area_id,charge_code_id,checked,rxl,yxl); 
	    return vo;
	  }

    //设置单个适用区域
	  @RequestMapping("/area/set-all-area")
	  @ResponseBody
	  public ResultVO setAllChargeArea(AreaChargeCodeListVO list) {
//	  	ResultVO vo = chargeCodeRuleService.createOneChargeArea(list); 
		  System.out.println(list);
	    return null;
	  }
	  
}
