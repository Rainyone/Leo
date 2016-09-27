package com.larva.inf.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlRewriteFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(UrlRewriteFilter.class);

	private void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain) 
					throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (StringUtils.isBlank(uri)) {
			request.getRequestDispatcher("/fail").forward(request, response);
			return;
		}
		
		if (uri.indexOf("/callback") > 0) {
			StringBuffer url = request.getRequestURL() ;
			String servletPath=request.getServletPath();  
			String queryStr = request.getQueryString();
			String charge_id = servletPath.substring(servletPath.indexOf("callback/")+9,servletPath.length());
			request.setAttribute("charge_id", charge_id);
			request.getRequestDispatcher("/callback?charge_id="+charge_id+"&"+queryStr).forward(request, response);
			return;
		}
		
		if (uri.indexOf("/charge") == -1)	{
			request.getRequestDispatcher("/fail").forward(request, response);
			return;
		}
		
		String contextPath = request.getContextPath();
		if (!validateParam(request, contextPath)) {
			request.getRequestDispatcher("/fail").forward(request, response);
			return;
		}
		String plainText = "";
		try {
			plainText = decodeUrl(request, contextPath);
		} catch (Exception e) {
			// 解析报错，可能是模拟访问或者无效请求，记录日志并返回错误页面
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			log.error("exception error : " + sw.toString());
			request.getRequestDispatcher("/fail").forward(request, response);
			return;
		}
		
		if (!urlCheck(plainText)) {
			request.getRequestDispatcher("/fail").forward(request, response);
			return;
		}
		
		if (checkAuth(request, plainText)) {
			request.getRequestDispatcher(plainText).forward(request, response);
			return;
		} else {
			request.getRequestDispatcher("/fail").forward(request, response);
			return;
		}
	}
	
	/**
	 * <p><b>Title:</b> checkAuth</p>
	 * <p><b>Description:</b> 业务路径确认方法</p>
	 * @author root
	 * @param request
	 * @param plainText
	 * @return
	 */
	private boolean checkAuth(HttpServletRequest request, String plainText) {
		// 进行参数拆分
		String paramStr = plainText.substring(plainText.indexOf("?") + 1);
		String params[] = paramStr.split("&");
		HashMap<String, String> paramsMaps = new HashMap<String, String>();
		if (params == null || params.length < 4) {
			log.info("参数太少，小于4个 ");
			return false;
		}
		
		for (int i = 0; i < params.length; i++) {
			String[] temp = params[i].split("=");
			if (temp != null && temp.length > 1)
				paramsMaps.put(temp[0], temp[1]);
		}
		
		// 检查是否有必要的参数
		if (paramsMaps == null || !paramsMaps.containsKey("app_id")
				|| !paramsMaps.containsKey("app_key")
				|| !paramsMaps.containsKey("timestamp")
				|| !paramsMaps.containsKey("imei")) {
			log.info("没有关键参数  app_id: " + paramsMaps.containsKey("app_id") + 
					" app_key: " + paramsMaps.containsKey("app_key") + 
					" timestamp: " + paramsMaps.containsKey("timestamp")+ 
					" imei: " + paramsMaps.containsKey("imei"));
			return false;
		}
		
		String sign = request.getParameter("sign");
		String appId = paramsMaps.get("app_id");
		String appSecret = paramsMaps.get("app_key");
		String imei = paramsMaps.get("imei");
		String timestamp = paramsMaps.get("timestamp");
		String sign2 = DESEncrypt.getSign(appId, appSecret,imei, timestamp);
		if (!sign.equals(sign2)) {
			log.info("签名错误 sign:" + sign + " sign2: " + sign2);
			return false;
		}
		
		return true;
	}
	
	/**
	 * <p><b>Title:</b> urlCheck</p>
	 * <p><b>Description:</b> 判断是否是URL</p>
	 * @author root
	 * @param url
	 * @return
	 */
	private static boolean urlCheck(String url) {
		String regex = "[-a-zA-Z0-9+&@#/%?=~_|!:,.;\\(\\)\u4E00-\u9FA5]*[-a-zA-Z0-9+&@#/%=~_|\\(\\)\u4E00-\u9FA5]";
		Pattern patt = Pattern.compile(regex);
		Matcher matcher = patt.matcher(url);
		boolean isMatch = matcher.matches();
		if (!isMatch) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * <p><b>Title:</b> validateParam</p>
	 * <p><b>Description:</b> 验证参数</p>
	 * @author root
	 * @param request
	 * @param response
	 * @param contextPath
	 * @return
	 * @throws IOException
	 */
	private boolean validateParam(HttpServletRequest request, String contextPath) {
		if (request.getParameterMap().size() != 2) 
			return false;
		
		String sign = request.getParameter("sign");
		String data = request.getParameter("body");
		log.info("SessionID:" + request.getSession().getId() + " sign:" + sign + " data: " + data);
		// 无效请求，进行一个记录并返回错误页面
		if (StringUtils.isBlank(sign) || StringUtils.isBlank(data)) {
			log.info("error request host: " + request.getRemoteHost() + 
					" addr: " + request.getRemoteAddr() + " port: " + request.getServerPort() + 
					" name: " + request.getServerName() + " path: " + request.getServletPath());
			return false;
		}
		return true;
	}
	
	/**
	 * <p><b>Title:</b> decodeUrl</p>
	 * <p><b>Description:</b> 解码URL路径</p>
	 * @author root
	 * @param request
	 * @param contextPath
	 * @return
	 * @throws IOException
	 * @throws Exception
	 * @throws Base64DecodingException
	 */
	private String decodeUrl(HttpServletRequest request, 
			String contextPath) throws IOException, Exception {
		String data = request.getParameter("body");
		String plainText = URIUtil.getPathQuery(new String(
				DESEncrypt.decrypt(Base64.decodeBase64(data))));
		
		log.info("plaintext: " + plainText);
		return plainText;
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		this.doFilterInternal((HttpServletRequest) request,
				(HttpServletResponse) response, filterChain);
	}

	@Override
	public void destroy() {
	}
	
}
