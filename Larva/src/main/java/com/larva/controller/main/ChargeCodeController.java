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
@RequestMapping("/main/charge_code")
public class ChargeCodeController {
	@Autowired
	private IAppManageService appManageService;
	//跳转到app管理
    @RequestMapping("/manage")
    public String departmentManage() {
        return "main/charge_code/manage";
    }
}
