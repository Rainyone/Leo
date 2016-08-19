package com.mini.core;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.mini.core.utils.AnnotationUtil;
import com.mini.core.utils.MiniUtil;
import com.mini.core.utils.StrKit;

public class StrongEntityInterceptor implements MethodInterceptor{
	private final Log logger = LogFactory.getLog(StrongEntityInterceptor.class);
	@Override
	public Object intercept(Object obj, Method method, Object[] arg, MethodProxy proxy) throws Throwable {  
		logger.debug("Before:"+method);    
        Object object=proxy.invokeSuper(obj, arg); 
        if(obj instanceof StrongEntity &&method.getName().startsWith("set")){
        	String field = AnnotationUtil.getColumnFieldName(MiniUtil.getStrongClazz(obj.getClass()).getClass(), method);
        	if(StrKit.notBlank(field))
        		((StrongEntity)obj).addModifyFlag(field);
        }
        logger.debug("After:"+method);   
        return object;  
    }  
}
