package com.leyou.filter;

import com.leyou.common.utils.CookieUtils;
import com.leyou.common.utils.JwtUtils;
import com.leyou.config.FilterProperties;
import com.leyou.config.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Date:2021/03_3:55 下午
 * @Description：
 */
@Component
@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})
public class LoginFilter extends ZuulFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private FilterProperties filterProperties;

    @Override
    public String filterType() {
        return "pre";       //前置过滤器，在路由之前拦截
    }

    @Override
    public int filterOrder() {
        return 5;       //优先级（暂时随便写）
    }

    @Override
    public boolean shouldFilter() {     //返回true 就执行过滤器（也就是下面的run方法），返回false不执行

        List<String> allowPaths = filterProperties.getAllowPaths();     //获取白名单路径

        RequestContext context = RequestContext.getCurrentContext();    //获取初始化运行上下文
        HttpServletRequest request = context.getRequest();      //获取request
        String url = request.getRequestURL().toString();    //获取当前请求路径

        for (String allowPath : allowPaths){
            if (StringUtils.contains(url, allowPath)){  //将当前请求逐个匹配白名单的请求，若是白名单上的则return false 立即放行
                return false;
            }
        }

        return true;    //执行拦截
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();    //获取初始化运行上下文
        HttpServletRequest request = context.getRequest();      //获取request

        //1、获取token
        String token = CookieUtils.getCookieValue(request, this.jwtProperties.getCookieName());

        //2、校验
        try {
            //校验通过，放行
            JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
        } catch (Exception e) {
            //校验出现异常，返回403
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        }

        return null;
    }
}
