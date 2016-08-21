package com.larva.controller.main;

import com.larva.model.Department;
import com.larva.service.IRoleService;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.RoleCreateVO;
import com.larva.vo.RoleEditVO;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author sxjun
 * @time 2015/9/1 16:28
 */
@Controller
@RequestMapping("/main/role")
public class RoleController {
    @Resource
    private IRoleService roleService;

    //跳转到角色管理页面
    @RequestMapping("/manage")
    public String manage() {
        return "main/role/manage";
    }

    //获取显示的角色
    @RequestMapping(value = "/show-roles")
    public
    @ResponseBody
    Pager<Map<String,Object>> getShowRoles(PagerReqVO pagerReqVO) {
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        Pager<Map<String,Object>> vo = roleService.getShowRoles(pagerReqVO,principal.toString());
        return vo;
    }
    
    //获取所有角色树
    @RequestMapping(value="/get-all-roles")
    public
    @ResponseBody
    ResultVO getAllDeparts() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        ResultVO vo = roleService.getRoleTree(principal.toString());
        return vo;
    }

    //创建角色
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO createRole(@Valid @ModelAttribute RoleCreateVO createVO, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ResultVO resultVO = new ResultVO(true);

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        resultVO = roleService.createRole(createVO, principal.toString());
        return resultVO;
    }

    //编辑角色
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO editRole(@Valid @ModelAttribute RoleEditVO editVO, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ResultVO resultVO = new ResultVO(true);

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }
        resultVO = roleService.editRole(editVO);
        return resultVO;
    }

    //删除角色
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO delRole(@RequestParam("roleIds[]") String[] roleIds) {
        ResultVO resultVO = roleService.deleteRole(roleIds);
        return resultVO;
    }

    //角色授权
    @RequestMapping(value = "/grant", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO grantRolePermission(@RequestParam String id,@RequestParam(value = "peridArray[]",required = false) String []peridArray) {
        ResultVO resultVO = roleService.grantPermissions(id,peridArray);
        return resultVO;
    }

}
