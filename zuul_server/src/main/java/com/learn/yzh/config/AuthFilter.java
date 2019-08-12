package com.learn.yzh.config;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.learn.yzh.model.UniVerResponse;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 路由拦截
 *
 *
 * >>>>>zuul的filter过滤器的生命周期有一下四个：
 *
 *  PRE： 这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
 *  ROUTING：这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache HttpClient或Netfilx Ribbon请求微服务。
 *  POST：这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
 *  ERROR：在其他阶段发生错误时执行该过滤器。 除了默认的过滤器类型，Zuul还允许我们创建自定义的过滤器类型。例如，我们可以定制一种STATIC类型的过滤器，直接在Zuul中生成响应，而不将请求转发到后端的微服务。
 *
 *
 *  Zuul中默认实现了很多Filter，也可以自己自定义过滤器
 *
 *  下面是自己自定义过滤器
 *  实际使用中我们可以结合shiro、oauth2.0等技术去做鉴权、验证
 *
 */
@Component
public class AuthFilter extends ZuulFilter {

    //每秒产生500个令牌（请求进来后会拿去一个令牌，如果没拿到就不让访问。）
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(1000);

    @Override
    public String filterType() {

//        PRE_TYPE:可以在请求被路由之前调用
//        ROUTING_TYPE:路由请求时被调用
//        POST_TYPE:在routing和error过滤器之后被调用
//        ERROR_TYPE:处理请求时发生错误时被调用

        return PRE_TYPE;//可以在请求被路由之前调用
    }

    @Override
    public int filterOrder() {
        return 0;//filter执行顺序，通过数字指定 ,优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        //zool 使用时，上下文对象RequestContext，是共享的。 所以通过RequestContext 获取值
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //设置需要拦截的路径
        if (request.getRequestURI().contains("/member/")) {
            //拦住了
            return true;
        }
        //放行
        return false;
    }

    /**
     *  filter需要执行的具体操作
     *
     * 例如：本filter实际执行的逻辑 是验证所有的访问请求中，是否包含安全信息auth
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() {

        //zool 使用时，上下文对象RequestContext，是共享的。 所以通过RequestContext 获取值
        RequestContext requestContext = RequestContext.getCurrentContext();
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String auth = request.getParameter("auth");
        //TODO 此处可以做日志记录
        System.out.println("zuul拦截--请求前验证---auth："+auth);

        //限流  如果qps 访问过高（超过上面定义的，就停止访问）
        if (!RATE_LIMITER.tryAcquire()||StringUtils.isBlank(auth)) {
            //如果token 等于null   返回状态码和没有权限的信息给前台
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        //成功的情况
        if (StringUtils.isNotBlank(auth)){
            ctx.setSendZuulResponse(true); //对请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
        }else {
            //失败的情况
            UniVerResponse res = new UniVerResponse();
            res.beFalse3("zuul拦截--请求前验证---没有auth登录验证",UniVerResponse.ERROR_BUSINESS);

            ctx.setSendZuulResponse(false); //不对请求进行路由
            ctx.setResponseStatusCode(res.getCode());//设置返回状态码
            ctx.setResponseBody(JSON.toJSONString(res));//设置返回响应体
            ctx.set("isSuccess", false);
            ctx.getResponse().setContentType("application/json;charset=UTF-8");//设置返回响应体格式，可能会乱码

        }

        return null;
    }

}