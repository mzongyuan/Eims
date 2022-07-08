package com.eims.filter;

import com.alibaba.fastjson.JSONObject;
import com.eims.common.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 请求过滤器（验证Token）
 * @Auth zongyuan.ma
 * @Date 2022/7/7 22:32
 * @Version V 1.0.0
 */
@Order(1)
@WebFilter(filterName = "httpRequestFilter", urlPatterns = "/*")
@Slf4j
public class HttpRequestFilter implements Filter {

    @Autowired
    private RedisUtil redisUtil;

    public List<String> ignoreUrl
            = Arrays.asList(
                    "/v1/login/front");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        // 获取请求Url
        String requestUrl = httpRequest.getRequestURI();
        // 判断Url是否需要跳过验证
        if (ignoreUrl.contains(requestUrl)) {
            filterChain.doFilter(httpRequest, httpResponse);
        } else {
            // 获取请求头Token
            String requestToken = httpRequest.getHeader("token");
            // Token为空或者null,返回401
            if (StringUtils.isEmpty(requestToken)) {
                httpResponse.setStatus(Constant.UNAUTHORIZED);
                httpResponse.setContentType("application/json; charset=UTF-8");
                httpResponse.getWriter().write(JSONObject.toJSONString(Result.failure(ResultCode.TOKEN_NONE)));
            }
            // 解析Token，判断Token是否过期
            String userCode = TokenUtils.tokenToOut(requestToken);
            // 通过Usercode获取Redis的token信息进行验证
            String tokens = (String) redisUtil.get(userCode);
            if (!StringUtils.isEmpty(tokens)) {
                // Token验证通过，继续执行
                filterChain.doFilter(httpRequest, httpResponse);
            } else {
                // Token失效
                httpResponse.setStatus(Constant.UNAUTHORIZED);
                httpResponse.setContentType("application/json; charset=UTF-8");
                httpResponse.getWriter().write(JSONObject.toJSONString(Result.failure(ResultCode.TOKEN_NONE)));
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
