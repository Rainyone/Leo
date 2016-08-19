package com.larva.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.larva.service.IMenuService;
import com.larva.service.IPermissionService;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;

/**
 * @author sxjun
 * @time 2015/8/12 13:49
 */
@Controller
@RequestMapping("/main")
public class MainController {
    @Resource
    private IMenuService menuService;
    @Resource
    private IPermissionService permissionService;

    //跳转到主页
    @RequestMapping("/index")
    public String index() {
        return "main/index";
    }


    //获取显示权限
    @RequestMapping("/get-show-permissions")
    public
    @ResponseBody
    Pager<Map<String,Object>> getShowPermissons(PagerReqVO pagerReqVO,TreeNode tree) {
    	Pager<Map<String,Object>> vo = permissionService.getShowPermissions(pagerReqVO,tree);
        return vo;
    }
    
  //获取所有部门树
    @RequestMapping(value="/get-all-permissions")
    public
    @ResponseBody
    ResultVO getAllDeparts() {
        ResultVO vo = permissionService.getPermissionTree();
        return vo;
    }
    
   /* //获取显示权限
    @RequestMapping("/get-show-permissions-except-children")
    public
    @ResponseBody
    ResultVO getShowPermissonsExceptChildren(@RequestParam Integer perId) {
        ResultVO resultVO = permissionService.getShowPermissions(perId);
        return resultVO;
    }*/

     //获取菜单显示权限
    @RequestMapping("/get-menu-show-permissions")
    public
     @ResponseBody
     ResultVO getMenuShowPermissons(@RequestParam int menuId) {
         ResultVO resultVO =permissionService.getMenuShowPermissions(menuId,true);
         return resultVO;
     }
    
    @RequestMapping("/get-menu-check-permissions")
    public
     @ResponseBody
     ResultVO getMenucheckPermissons(@RequestParam int menuId) {
         ResultVO resultVO =permissionService.getMenuShowPermissions(menuId,false);
         return resultVO;
     }
    
    /* @RequestMapping("/get-menu-show-permissions")
   public
    @ResponseBody
    ResultVO getMenuShowPermissons(@RequestParam int menuId) {
        ResultVO resultVO =permissionService.getMenuShowPermissions(menuId);
        return resultVO;
    }*/

     //获取角色显示权限
    @RequestMapping("/get-role-show-permissions")
    public
    @ResponseBody
    ResultVO getRoleShowPermissons(@RequestParam int roleId) {
        ResultVO resultVO =permissionService.getRoleShowPermissions(roleId,true);
        return resultVO;
    }
    
    //获取角色显示权限
    @RequestMapping("/get-role-check-permissions")
    public
    @ResponseBody
    ResultVO getRoleCheckPermissons(@RequestParam int roleId) {
    	ResultVO resultVO =permissionService.getRoleShowPermissions(roleId,false);
    	return resultVO;
    }
  /*  @RequestMapping("/get-role-show-permissions")
    public
    @ResponseBody
    ResultVO getRoleShowPermissons(@RequestParam int roleId) {
        ResultVO resultVO =permissionService.getRoleShowPermissions(roleId);
        return resultVO;
    }*/
    
    //跳转到欢迎页面
    @RequestMapping("/welcomePage")
    public String welcomePage(){
        return "main/welcomePage";
    }

}
