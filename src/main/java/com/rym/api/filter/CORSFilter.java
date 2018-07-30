package com.rym.api.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rym.module.redis.JedisFactory;

import lombok.extern.log4j.Log4j2;

/**
 * CORS过滤器
 * @author: zqy
 * @date: 2018/4/19 10:51
 * @since: 1.0-SNAPSHOT
 * @note: none
 */
@Log4j2
public class CORSFilter extends OncePerRequestFilter {
    String urlPrefix = null;

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String uri = request.getRequestURI();
        final String contextPath = request.getContextPath();
        final String resourcePath = uri.substring(contextPath.length(), uri.length());
        if (StringUtils.hasText(urlPrefix) && !uri.startsWith(urlPrefix)) {
            if (uri.startsWith("/null/")) {
                return;
            }

            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "86400");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, key, reqSource, sid, token");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            final String method = request.getMethod();
            log.debug("The {} request uri: {}", method, uri);

            if (!resourcePath.startsWith("/pay/") && !resourcePath.contains("swagger") 
            		&& !resourcePath.contains("/v2/api-docs")
            		&& !resourcePath.startsWith("/dcxy/api/locker/app/test")
            		&& !resourcePath.startsWith("/dcxy/api/locker/manage/test")) {
                if (!method.equalsIgnoreCase("OPTIONS") && !validateToken(request, response)) {
                    return;
                }
            }

            filterChain.doFilter(request, response);

            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean validateToken(HttpServletRequest request, HttpServletResponse response) {
    	Boolean isValidToken = false;
    	if(isValidToken) {
	        final String token = request.getHeader("token");
	        log.debug("The request header parameter token is: {}", token);
	        if (!StringUtils.hasText(token)) {
	            log.error("unable to get the token from the current request header");
	            output(response, "{\"code\": -1, \"msg\": \"获取用户请求会话信息失败，请重新进行登录\"}");
	            return false;
	        }
	
	        if ("Test321".equals(token)) {
	            return true;
	        }
	
	        ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
	        if (null != ac) {
	            JedisFactory jedisFactory = ac.getBean("jedisFactory", JedisFactory.class);
	            if (null != jedisFactory) {
	                final String reqSource = request.getHeader("reqSource");
	                int timeoutInSeconds = StringUtils.hasText(reqSource) && "app".equals(reqSource) ? 604800 : 1800;
	                String rkey = token;
	                if (604800 == timeoutInSeconds) {
	                    rkey += "time";
	                }
	
	                log.debug("The request header parameter: reqSource={}, timeoutInSeconds={}", reqSource, timeoutInSeconds);
	
	                try {
	                    final String value = jedisFactory.getString(token);
	                    if (StringUtils.hasText(value)) {
	                        if (604800 == timeoutInSeconds) {
	                            final String rval = jedisFactory.getString(rkey);
	                            SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	                            String day = sdfDay.format(new Date());
	                            log.debug("The app token info: key={}, value={}, day={}", rkey, rval, day);
	                            if (!StringUtils.hasText(rval)) {
	                                return jedisFactory.setString(token, value, timeoutInSeconds) && jedisFactory.setString(rkey, day, timeoutInSeconds);
	                            } else {
	                                if (!rval.equals(day)) {
	                                    return jedisFactory.setString(token, value, timeoutInSeconds) && jedisFactory.setString(rkey, day, timeoutInSeconds);
	                                }
	                                return true;
	                            }
	                        }
	                        return jedisFactory.setString(token, value, timeoutInSeconds);
	                    }
	
	                    log.warn("not found the token {} from redis server", token);
	                    output(response, "{\"code\": -2, \"msg\": \"请求会话已过期，请重新进行登录\"}");
	                } catch (Exception e) {
	                    log.error("get user info from redis failed: ", e);
	                    output(response, "{\"code\": -1, \"msg\": \"获取用户请求会话信息失败，请重新进行登录\"}");
	                }
	            }
	        } else {
	            log.error("unable to get the WebApplicationContext instance from the current request session[{}]", request.getSession().getId());
	            output(response, "{\"code\": -1, \"msg\": \"获取用户请求会话信息失败，请重新进行登录\"}");
	        }
	        return false;
    	}else {
    		return true;
    	}
       
    }

    protected void output(HttpServletResponse response, String content) {
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        try {
            PrintWriter writer = response.getWriter();
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error("write data into response failed: ", e);
            e.printStackTrace();
        }
    }
}
