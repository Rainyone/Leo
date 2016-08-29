package com.larva.controller.main;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import com.larva.vo.AppManageCreateVO;
import com.larva.vo.AppManageEditVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;

@Controller
@RequestMapping("/main/app")
public class AppController {
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
    Pager<Map<String,Object>> getListMenus(PagerReqVO pagerReqVO,TreeNode tree) {
    	Pager<Map<String,Object>> vo = appManageService.getAppManages(pagerReqVO);
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
}
