package com.example.cffexmember.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;

public class CookieUtil {
    public String readCookieValue(HttpServletRequest request,
                                  String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();  // 返回找到的cookie值
                }
            }
        }
        return null;  // 如果没有找到指定的cookie，返回null
    }

}
