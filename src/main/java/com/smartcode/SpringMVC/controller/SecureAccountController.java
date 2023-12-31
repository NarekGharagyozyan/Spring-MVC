package com.smartcode.SpringMVC.controller;

import com.smartcode.SpringMVC.service.user.UserService;
import com.smartcode.SpringMVC.util.constants.Parameter;
import com.smartcode.SpringMVC.util.constants.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class SecureAccountController {

    private final UserService userService;
    @PostMapping(value = "/secure/deleteAccount")
    public ModelAndView deleteAccount(@SessionAttribute(Parameter.EMAIL_PARAMETER) String email) {

        try {
            userService.deleteAccount(email);
            return new ModelAndView(Path.LOGIN_PATH);
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject(Parameter.MESSAGE_ATTRIBUTE,e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping(value = "/secure/logout")
    public String logout(HttpSession session,
                         HttpServletResponse resp,
                         @CookieValue(name = Parameter.REMEMBER_COOKIE, required = false) Cookie cookie) {

        session.invalidate();

        if (cookie != null){
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        }
        return Path.LOGIN_PATH;
    }

    @PostMapping(value = "/secure/change")
    public ModelAndView changePassword(@RequestParam String newPassword,
                                       @RequestParam String repeatPassword,
                                       @SessionAttribute(Parameter.EMAIL_PARAMETER) String email) {

        try {
            userService.changePassword(email,newPassword,repeatPassword);
            return new ModelAndView(Path.HOME_PATH);
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView(Path.CHANGE_PASSWORD_PATH);
            modelAndView.addObject(Parameter.MESSAGE_ATTRIBUTE,e.getMessage());
            return modelAndView;
        }
    }


}
