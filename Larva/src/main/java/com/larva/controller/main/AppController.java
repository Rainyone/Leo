package com.larva.controller.main;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.larva.service.IAppManageService;
import com.larva.vo.AppManageCreateVO;
import com.larva.vo.MenuCreateVO;
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
    ResultVO createAppManage(@Valid @ModelAttribute AppManageCreateVO createVO, BindingResult bindingResult) {
        ResultVO resultVO = new ResultVO(true);
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }

        resultVO = appManageService.saveAppManage(createVO);
        return resultVO;
    }
}
