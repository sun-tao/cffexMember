//package com.example.cffexmember.config;
//
//
//import com.example.cffexmember.interceptor.LoginInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    @Autowired
////    private LoginInterceptor loginHandlerInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration ir = registry.addInterceptor(loginHandlerInterceptor);
//        ir.addPathPatterns("/**");
//        // 改进排除路径，考虑斜杠变体
//        ir.excludePathPatterns(
//                "/login",
//                "/login/**",
//                "/logout",
//                "/logout/",
//                // 排除静态资源
//                "/static/**",
//                "/resources/**"
//        );
//    }
//}