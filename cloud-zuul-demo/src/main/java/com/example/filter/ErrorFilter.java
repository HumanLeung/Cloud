package com.example.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

/**
 * @author Administrator
 */
@Slf4j
@Component
public class ErrorFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext rc = RequestContext.getCurrentContext();
        Throwable throwable = rc.getThrowable();
        log.error("ErrorFilter..." + throwable.getCause().getMessage(),throwable);
        //响应状态码
        rc.setResponseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        rc.getResponse().setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = rc.getResponse().getWriter()) {
            writer.print("{\"message\":\":\"" + HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
