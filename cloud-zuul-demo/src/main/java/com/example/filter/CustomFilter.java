package com.example.filter;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class CustomFilter extends ZuulFilter {
    /**
     * 过滤器类型
     *  pre
     *  routing
     *  post
     *  error
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 动作（具体操作）
     *      具体逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext rc = RequestContext.getCurrentContext();
        HttpServletRequest request = rc.getRequest();
        log.info("CustomerFilter...method={}, url={}",request.getMethod(), request.getRequestURI());
        return null;
    }
}
