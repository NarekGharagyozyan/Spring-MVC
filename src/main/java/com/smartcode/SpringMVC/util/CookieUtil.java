package com.smartcode.SpringMVC.util;

import javax.servlet.http.Cookie;

public class CookieUtil {
    public static String getCookieValueByName(Cookie[] cookies, String name){
        for (Cookie cookie: cookies){
            if (cookie.getName().equals(name))
                return cookie.getValue();
        }
        return null;
    }

    public static Cookie getCookieByName(Cookie[] cookies, String name){
        for (Cookie cookie: cookies){
            if (cookie.getName().equals(name))
                return cookie;
        }
        return null;
    }
}
