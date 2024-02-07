package com.example.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class AccessFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取上下文
        RequestContext rc = RequestContext.getCurrentContext();
        HttpServletRequest request = rc.getRequest();
        //获取表单token
        String token = request.getParameter("token");

        if (null == token){
            log.warn("token is null...");
            //请求结束，不再往下请求。
            rc.setSendZuulResponse(false);
            rc.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            //相应类型
            rc.getResponse().setContentType("application/json; charset=utf-8");
            try (PrintWriter writer = rc.getResponse().getWriter()) {
                writer.print("{\"message\":\":\"" + HttpStatus.UNAUTHORIZED.getReasonPhrase() + "\"}");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            log.info("token is ok!");
        }
        return null;
    }
}
