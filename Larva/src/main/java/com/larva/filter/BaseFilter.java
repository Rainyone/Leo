package com.larva.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import com.larva.utils.ThreadLocalUtil;

import java.io.IOException;

/**
 * @author sxjun
 * @time 2015/8/12 16:27
 */
public class BaseFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        ThreadLocalUtil.setBasePath(basePath);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
