package com.larva.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Producer;
import com.larva.enums.SessionEnum;
import com.larva.ip2region.DataBlock;
import com.larva.ip2region.DbConfig;
import com.larva.ip2region.DbSearcher;
import com.larva.model.LoginLog;
import com.larva.service.IAccountService;
import com.larva.service.ILogService;
import com.larva.utils.Constants;
import com.larva.utils.DataUtil;
import com.larva.utils.FileKit;
import com.larva.utils.PathKit;
import com.larva.utils.UUIDUtil;
import com.larva.vo.LoginVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;
import com.larva.vo.UserChangePasswordVO;
import com.larva.vo.UserCreateVO;
import com.larva.vo.UserEditDepVO;
import com.larva.vo.UserEditVO;

/**
 * @author sxjun
 */
@Controller
@RequestMapping("/user")
public class UserController{
    @Resource
    private IAccountService accountService;

    @Resource
    private ILogService logService;
    
    
    
    /* @Resource
       private JmsTemplate jmsTemplate;
    */
	/* @Resource
	    private RedisManager redisManager;
	*/
//    @Resource
//    private Producer captchaProducer;

    //跳转到用户管理页面
    @RequestMapping("/manage")
    public String manage() {
        return "user/manage";
    }
    
  //用户管理  获取数据
    @RequestMapping(value = "/get-manage-user")
    public
    @ResponseBody
    Pager<Map<String,Object>> getManageUser(PagerReqVO pagerReqVO,TreeNode tree) {
    	Pager<Map<String,Object>> vo = accountService.getPageUsers(pagerReqVO,tree);
        return vo;
    }

    /*//用户管理  获取数据
    @RequestMapping(value = "/get-manage-user", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO getManageUser(int page, int rows) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        ResultVO resultVO = accountService.selectAccount(Integer.parseInt(principal.toString()), page, rows);
        return resultVO;
    }*/

    //跳转到登录页面
    @RequestMapping("/login")
    public String login() {
        return "user/login";
    }

    //退出
    @RequestMapping("/exit")
    public String exit() {
        SecurityUtils.getSubject().logout();
        return "user/login";
    }

   /* //验证码图片
    @RequestMapping("/authCode")
    public void authCode(HttpServletResponse response, HttpSession session) throws IOException {
        String text = captchaProducer.createText();
        BufferedImage image = captchaProducer.createImage(text);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "JPG", outputStream);
        session.setAttribute(SessionEnum.VERIFYCODE.toString(), text);
        outputStream.flush();
        outputStream.close();
    }*/

    //验证登录
    @RequestMapping(value = "/check-login", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO checkLogin(@Valid @ModelAttribute final LoginVO loginVO, BindingResult bindingResult, HttpSession session, HttpServletRequest request) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ResultVO resultVO = new ResultVO(true);

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }
        //验证验证码
       /* Object verifyCodeObj = session.getAttribute(SessionEnum.VERIFYCODE.toString());
        if (verifyCodeObj == null) {
            resultVO.setOk(false);
            resultVO.setMsg("验证码已过期");
            return resultVO;
        }

        if (!verifyCodeObj.toString().equalsIgnoreCase(loginVO.getVerifycode())) {
            resultVO.setOk(false);
            resultVO.setMsg("验证码错误");
            return resultVO;
        }*/
        
        //验证登录
        UsernamePasswordToken token = new UsernamePasswordToken(loginVO.getAccount(), loginVO.getPassword());
        token.setRememberMe(loginVO.isRememberMe()); 
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            resultVO.setOk(false);
            resultVO.setMsg("账号或者密码错误");
            return resultVO;
        }
        //删除验证码
//        session.removeAttribute(SessionEnum.VERIFYCODE.toString());
        
        session.setAttribute(Constants.DEFAULT_SESSION_USERNAME, loginVO.getAccount());

        final String ip = DataUtil.getIpAddr(request);
        
        DataBlock fdata = null;
        try {
        	DbSearcher _searcher = new DbSearcher(new DbConfig(), PathKit.getRootClassPath()+File.separator+"ip2region"+File.separator+"ip2region.db");
        	fdata = _searcher.binarySearch(ip);
        	System.out.println(fdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
        LoginLog l = new LoginLog();
        l.setAccount(loginVO.getAccount());
//        l.setAddress(address);
//        l.setCity(city);
        l.setCityCode(fdata!=null?String.valueOf(fdata.getCityId()):"");
        l.setDetailAddress(fdata!=null?String.valueOf(fdata.getRegion()):"");
        l.setId(UUIDUtil.getUUID());
        l.setLoginIp(ip);
        l.setLoginTime(new Date());
//        l.setPointX(pointX);
//        l.setPointY(pointY);
//        l.setProvince(province);
        logService.insertLoginLog(l);
        //发送日志
     /*   jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                message.setString("type", "login");
                message.setString("account", loginVO.getAccount());
                message.setString("ip", ip);
                message.setString("logintime", String.valueOf(System.currentTimeMillis()));
                return message;
            }
        });
        Map<String, String> message = new HashMap<>();
        message.put("type", "login");
        message.put("account", loginVO.getAccount());
        message.put("ip", ip);
        message.put("logintime", String.valueOf(System.currentTimeMillis()));
        redisManager.publish("logs", JSON.toJSONString(message));*/


        return resultVO;
    }

    //添加用户
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO create(@Valid @ModelAttribute final UserCreateVO createVO, BindingResult bindingResult, HttpSession session, HttpServletRequest request) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ResultVO resultVO = new ResultVO(true);

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }
        resultVO = accountService.saveUser(createVO);
        return resultVO;
    }
    
  //编辑部门
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO editDepartment(@Valid @ModelAttribute UserEditVO editVO, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ResultVO resultVO = new ResultVO(true);

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }
        resultVO = accountService.editUser(editVO);
        return resultVO;
    }

    //修改用户部门
    @RequestMapping(value = "/edit-user-dep", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO editUserDep(@Valid @ModelAttribute final UserEditDepVO userEditDepVO, BindingResult bindingResult, HttpSession session, HttpServletRequest request) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ResultVO resultVO = new ResultVO(true);

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }
        resultVO=accountService.updateUserDep(userEditDepVO.getUserId(),userEditDepVO.getDepId());
        return resultVO;
    }


    //删除用户
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO delete(@RequestParam("userIds[]") String[] userIds) {
        ResultVO resultVO = accountService.deleteUser(userIds);
        return resultVO;
    }

    //角色分配
    @RequestMapping(value = "/grant", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO grantUserRoles(@RequestParam String userId,@RequestParam(value = "roleArray[]",required = false) String [] roleArray) {
        ResultVO resultVO = accountService.grantRoles(userId,roleArray);
        return resultVO;
    }
  //修改密码
    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultVO changePassword(@Valid @ModelAttribute UserChangePasswordVO userChangePasswordVO, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ResultVO resultVO = new ResultVO(true);

        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            resultVO.setOk(false);
            resultVO.setMsg(defaultMessage);
            return resultVO;
        }
        resultVO = accountService.changePassword(userChangePasswordVO);
        return resultVO;
    }
    
}
