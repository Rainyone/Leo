package com.larva.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;

public class MyExceptionHandler implements HandlerExceptionResolver {    
      
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,  Exception ex) {    
            ModelAndView mv = new ModelAndView();  
            /*  使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常   */  
            FastJsonJsonView view = new FastJsonJsonView();  
            Map<String, Object> attributes = new HashMap<String, Object>();  
           // attributes.put("code", ex.get);  
            attributes.put("msg", ex.getMessage());  
            view.setAttributesMap(attributes);  
            mv.setView(view);   
           // log.debug("异常:" + ex.getMessage(), ex);  
            return mv;  
    }  
}   
