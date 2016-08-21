package com.larva.controller.main;

import com.larva.service.IPermissionService;
import com.larva.vo.PermissionCreateVO;
import com.larva.vo.PermissionEditVO;
import com.larva.vo.ResultVO;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by sxjun on 15-8-31.
 */
@RequestMapping("/main/permission")
@Controller
public class PermissionController {
    @Resource
    private IPermissionService permissionService;


    //跳转到权限管理页面
    @RequestMapping("/manage")
    public String managePermission() {
        return "main/permission/manage";
    }

    //创建权限
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO createPermission(@Valid @ModelAttribute PermissionCreateVO createVO, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ResultVO resultVO = new ResultVO(true);

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }

        resultVO = permissionService.cratePermission(createVO);
        return resultVO;
    }

    //删除权限
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO delPermission(@RequestParam("permIds[]") String[] permIds) {
        ResultVO resultVO = permissionService.delPermission(permIds);
        return resultVO;
    }

    //编辑权限
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO editPermission(@Valid @ModelAttribute PermissionEditVO editVO, BindingResult bindingResult) {
        ResultVO resultVO = new ResultVO(true);
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }
        resultVO = permissionService.editPermission(editVO);
        return resultVO;
    }
}
