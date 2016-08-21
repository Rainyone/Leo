package com.larva.controller.main;

import com.larva.service.IMenuService;
import com.larva.vo.MenuCreateVO;
import com.larva.vo.MenuEditVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by sxjun on 15-8-31.
 */
@RequestMapping("/main/menu")
@Controller
public class MenuController {
    @Resource
    private IMenuService menuService;
    
    @RequestMapping("/get-list-menus")
    public
    @ResponseBody
    Pager<Map<String,Object>> getListMenus(PagerReqVO pagerReqVO,TreeNode tree) {
    	Pager<Map<String,Object>> vo = menuService.getPageMenus(pagerReqVO,tree);
        return vo;
    }
    
    //获取所有菜单树
    @RequestMapping(value="/get-all-menus")
    public
    @ResponseBody
    ResultVO getAllMenus() {
        ResultVO vo = menuService.getMenuTree();
        return vo;
    }

    //获取显示菜单
    @RequestMapping("/get-show-menus")
    public
    @ResponseBody
    ResultVO getShowMenus() {
        ResultVO resultVO = new ResultVO(true);
        List<Map<String, Object>> maps = menuService.selectShowMenus(null);
        resultVO.setData(maps);
        return resultVO;
    }
    
   /* //获取显示菜单--不显示下级
    @RequestMapping("/get-show-menus-except-children")
    public
    @ResponseBody
    ResultVO getShowMenusExceptChildren(@RequestParam Integer menuId) {
        ResultVO resultVO = new ResultVO(true);
        List<Map<String, Object>> maps = menuService.selectShowMenus(menuId);
        resultVO.setData(maps);
        return resultVO;
    }*/

    //菜单管理
    @RequestMapping("/manage")
    public String manageMenu() {
        return "main/menu/manage";
    }

    //创建菜单
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO createMenu(@Valid @ModelAttribute MenuCreateVO createVO, BindingResult bindingResult) {
        ResultVO resultVO = new ResultVO(true);
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }

        resultVO = menuService.createMenu(createVO);

        return resultVO;
    }

    //编辑菜单
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO editMenu(@Valid @ModelAttribute MenuEditVO editVO, BindingResult bindingResult) {
        ResultVO resultVO = new ResultVO(true);
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }

        resultVO = menuService.editMenu(editVO);

        return resultVO;
    }

    //删除菜单
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO delMenu(@RequestParam("menuIds[]") String[] menuIds) {
        ResultVO resultVO = menuService.deleteMenu(menuIds);
        return resultVO;
    }

    //菜单授权
    @RequestMapping(value = "/grant", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO grantMenuPermission(@RequestParam String id,@RequestParam(value = "peridArray[]",required = false) String []peridArray) {
        ResultVO resultVO = menuService.grantPermissions(id,peridArray);
        return resultVO;
    }

}
