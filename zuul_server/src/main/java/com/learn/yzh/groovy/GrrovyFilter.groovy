package com.learn.yzh.groovy

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import com.netflix.zuul.exception.ZuulException

import javax.servlet.http.HttpServletRequest
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

class GrrovyFilter extends ZuulFilter{

    @Override
    String filterType() {
        return PRE_TYPE;
    }

    @Override
    int filterOrder() {
        return 10
    }

    @Override
    boolean shouldFilter() {
        return true
    }

    @Override
    Object run() throws ZuulException {
        HttpServletRequest request =  RequestContext.currentContext.request as HttpServletRequest
        Iterator headerIt = request.getHeaderNames().iterator()
        while (headerIt.hasNext()) {
            String name = (String) headerIt.next()
            String value = request.getHeader(name)
            println("header: " + name + ":" + value)
        }
        println("This Is Groovy Filter")
        return null
    }
}