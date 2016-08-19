package com.larva.exception;

import com.alibaba.fastjson.JSON;
import com.larva.utils.DataUtil;
import com.larva.utils.ThreadLocalUtil;
import com.larva.vo.ResultVO;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CommonExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        e.printStackTrace();
        httpServletResponse.setCharacterEncoding("UTF-8");
        boolean ajax = DataUtil.isAjax(httpServletRequest);
        if(ajax){
            ResultVO resultVO = new ResultVO(false);
            resultVO.setMsg("系统繁忙");
            try {
                httpServletResponse.getWriter().print(JSON.toJSONString(resultVO));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }
        ModelAndView modelAndView = new ModelAndView(ThreadLocalUtil.getBasePath()+"user/exit");
        return modelAndView;
    }
}
