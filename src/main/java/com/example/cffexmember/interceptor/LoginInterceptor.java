package com.example.cffexmember.interceptor;

import com.example.cffexmember.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        log.info("Intercepting path: {}", request.getRequestURI());
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //查询当前是否有登录
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                //有登录，则放行
                if ("cookie".equals(cookie.getName())) {
                    log.info("用户已登录，放行");
                    cookie.setMaxAge(30 * 60);
                    response.addCookie(cookie);
                    return true;
                }
            }
        }
        //没有登录，跳转到登录页
        response.sendRedirect("/loginPage");
        return false;
    }

}