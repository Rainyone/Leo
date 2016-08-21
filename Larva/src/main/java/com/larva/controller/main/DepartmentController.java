package com.larva.controller.main;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.larva.model.Department;
import com.larva.service.IDepartmentService;
import com.larva.vo.DepartmentCreateVO;
import com.larva.vo.DepartmentEditVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;

/**
 * @author sxjun
 * @time 2015/9/2 10:56
 */
@Controller
@RequestMapping("/main/department")
public class DepartmentController {
    @Resource
    private IDepartmentService departmentService;

    //跳转到部门管理
    @RequestMapping("/manage")
    public String departmentManage() {
        return "main/department/manage";
    }

    //获取显示的部门
    @RequestMapping(value="/get-list-departments")
    public
    @ResponseBody
    Pager<Map<String,Object>> getListDeparts(PagerReqVO pagerReqVO,TreeNode tree) {
    	Pager<Map<String,Object>> vo = departmentService.getPageDepartments(pagerReqVO,tree);
        return vo;
    }

    //获取所有部门树
    @RequestMapping(value="/get-all-departments")
    public
    @ResponseBody
    ResultVO getAllDeparts() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        ResultVO vo = departmentService.getDeptTree(principal.toString());
        return vo;
    }

  /*  //获取显示的部门
    @RequestMapping(value = "/get-show-departments", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO getShowDeparts(@RequestParam(required = false) Integer checkUserId) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        ResultVO resultVO = departmentService.getShowDepartments(Integer.parseInt(principal.toString()),null,checkUserId);
        return resultVO;
    }

    //获取显示的部门, 根据部门id不显示子级部门
    @RequestMapping(value = "/get-show-departments-except-children", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO getShowDepartsExceptChildrenDeps(@RequestParam int depId) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        ResultVO resultVO = departmentService.getShowDepartments(Integer.parseInt(principal.toString()),depId,null);
        return resultVO;
    }*/

    //创建部门
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO createDepartment(@Valid @ModelAttribute DepartmentCreateVO createVO, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ResultVO resultVO = new ResultVO(true);

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }

        resultVO = departmentService.createDepartment(createVO);
        return resultVO;
    }

    //编辑部门
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO editDepartment(@Valid @ModelAttribute DepartmentEditVO createVO, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ResultVO resultVO = new ResultVO(true);

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }
        Object principal = SecurityUtils.getSubject().getPrincipal();
        resultVO = departmentService.editDepartment(createVO,principal.toString());
        return resultVO;
    }

    //删除部门
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO delDepartment(@RequestParam("depIds[]") String[] depIds) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        ResultVO resultVO = departmentService.deleteDep(depIds, principal.toString());
        return resultVO;
    }


}
