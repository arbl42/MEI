package pt.uminho.miei.ras.controller.shared;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieHandler {
    public static void add(String id, HttpServletResponse response) {
        Cookie cookie = new Cookie("user", id);
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void remove(HttpServletResponse response) {
        Cookie cookie = new Cookie("user", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
