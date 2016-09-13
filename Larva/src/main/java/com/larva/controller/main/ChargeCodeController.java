package com.larva.controller.main;

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
import com.larva.service.IChargeCodeService;
import com.larva.utils.Constants;
import com.larva.vo.AppManageCreateVO;
import com.larva.vo.ChargeCodeCreateVO;
import com.larva.vo.ChargeCodeEditVO;
import com.larva.vo.MenuCreateVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;

@Controller
@RequestMapping("/main/charge_code")
public class ChargeCodeController {
	@Autowired
	private IChargeCodeService chargeCodeService;
	//跳转到计费代码管理
    @RequestMapping("/manage")
    public String chargeCodeManage() {
        return "main/charge_code/manage";
    }
    //获取所有计费代码
    @RequestMapping("/get-list-charge-codes")
    @ResponseBody
    public Pager<Map<String,Object>> getListChargeCodes(PagerReqVO pagerReqVO) {
    	Pager<Map<String,Object>> vo = chargeCodeService.getChargeCodes(pagerReqVO);
        return vo;
    }
    //创建计费代码
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO createChargeCode(@Valid @ModelAttribute ChargeCodeCreateVO createVO, BindingResult bindingResult, HttpSession session, HttpServletRequest request) {
        ResultVO resultVO = new ResultVO(true);
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }
        createVO.setCreate_people_name((String)session.getAttribute(Constants.DEFAULT_SESSION_USERNAME));
        resultVO = chargeCodeService.saveChargeCode(createVO);
        return resultVO;
    }
    
    //删除计费代码
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO delChargeCodes(@RequestParam("chargeCodeIds[]") String[] chargeCodeIds,HttpSession session, HttpServletRequest request) {
        ResultVO resultVO = chargeCodeService.deleteChargeCodes(chargeCodeIds,(String)session.getAttribute(Constants.DEFAULT_SESSION_USERNAME));
        return resultVO;
    }
    //修改计费代码
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO editChargeCode(@Valid @ModelAttribute ChargeCodeEditVO editVo, BindingResult bindingResult,HttpSession session, HttpServletRequest request) {
    	 ResultVO resultVO = new ResultVO(true);
         List<FieldError> fieldErrors = bindingResult.getFieldErrors();

         if (fieldErrors != null && !fieldErrors.isEmpty()) {
             String defaultMessage = fieldErrors.get(0).getDefaultMessage();
             resultVO.setOk(false);
             resultVO.setMsg(defaultMessage);
             return resultVO;
         }
         if(editVo.getId()==null||"".equals(editVo.getId())){//id不能为空
        	 resultVO.setOk(false);
             resultVO.setMsg("id不能为空");
             return resultVO;
         }
         editVo.setUpdate_people_name((String)session.getAttribute(Constants.DEFAULT_SESSION_USERNAME));
         resultVO = chargeCodeService.editChargeCode(editVo);
         return resultVO;
    }
}
