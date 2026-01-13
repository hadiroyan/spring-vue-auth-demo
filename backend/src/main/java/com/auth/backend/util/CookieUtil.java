package com.auth.backend.util;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CookieUtil {

    public void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        // cookie.setSecure(true);

        response.addHeader("Set-Cookie",
                String.format("%s=%s; Path=/; HttpOnly; Max-Age=%d; SameSite=Lax", name, value, maxAge));
    }

    public Optional<String> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(name))
                    .map(Cookie::getValue)
                    .findFirst();
        }

        return Optional.empty();
    }

    public void deleteCookie(HttpServletResponse response, String name) {
        response.addHeader("Set-Cookie",
                String.format("%s=; Path=/; HttpOnly; Max-Age=0; SameSite=Lax", name));
    }
}
