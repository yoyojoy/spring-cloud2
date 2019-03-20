package com.shengyecapital.common.interceptor;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.shengyecapital.common.constants.RequestHeaderConstants;
import com.shengyecapital.common.context.CustomHystrixContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class PropagateUserIdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!HystrixRequestContext.isCurrentThreadInitialized()) {
            HystrixRequestContext.initializeContext();
        }

        String userId = request.getHeader(RequestHeaderConstants.X_USER_ID);
        if (!StringUtils.isEmpty(userId)) {
            CustomHystrixContext.getInstance().setUserId(Integer.parseInt(userId));
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (HystrixRequestContext.isCurrentThreadInitialized()) {
            HystrixRequestContext.getContextForCurrentThread().shutdown();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

}
