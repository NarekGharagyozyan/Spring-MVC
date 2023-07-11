package com.smartcode.SpringMVC.controller;

import com.smartcode.SpringMVC.model.User;
import com.smartcode.SpringMVC.service.user.UserService;

import com.smartcode.SpringMVC.util.CookieUtil;
import com.smartcode.SpringMVC.util.constants.Parameter;
import com.smartcode.SpringMVC.util.constants.Path;
import com.smartcode.SpringMVC.util.encoder.AESManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
public class TestController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(@RequestParam String email,
                        @RequestParam String password,
                        @RequestParam (required = false) String rememberMe,
                        HttpServletResponse resp,
                        HttpSession session){


        try {
            userService.login(email,password);
            if (rememberMe  != null && rememberMe.equals("on")){
                Cookie cookie = new Cookie(Parameter.REMEMBER_COOKIE, AESManager.encrypt(email + ":" + password));
                cookie.setMaxAge(360000);
                resp.addCookie(cookie);
            }
            session.setAttribute(Parameter.EMAIL_PARAMETER, email);
            return new ModelAndView(Path.HOME_PATH);
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView(Path.INDEX_PATH);
            modelAndView.addObject(Parameter.MESSAGE_ATTRIBUTE, e.getMessage());
            return modelAndView;
        }
    }


    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ModelAndView register(@RequestParam String name,
                                 @RequestParam String lastname,
                                 @RequestParam Double balance,
                                 @RequestParam String email,
                                 @RequestParam String password,
                                 @RequestParam int age) {

        User user = new User();

        try {
            user.setName(name);
            user.setLastname(lastname);
            user.setAge(age);
            user.setEmail(email);
            user.setBalance(balance);
            user.setPassword(password);

            userService.register(user);
            return new ModelAndView(Path.INDEX_PATH);
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView(Path.REGISTER_PATH);
            modelAndView.addObject(Parameter.MESSAGE_ATTRIBUTE,e.getMessage());
            return modelAndView;
        }
    }

    @RequestMapping(value = "/deleteAccount",method = RequestMethod.POST)
    public ModelAndView deleteAccount(HttpSession session) {

        try {
            String email = (String)session.getAttribute(Parameter.EMAIL_PARAMETER);

            userService.deleteAccount(email);
            return new ModelAndView(Path.INDEX_PATH);
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject(Parameter.MESSAGE_ATTRIBUTE,e.getMessage());
            return modelAndView;
        }
    }

    @RequestMapping(value = "/change",method = RequestMethod.POST)
    public ModelAndView changePassword(HttpSession session,
                                       @RequestParam String newPassword,
                                       @RequestParam String repeatPassword) {

        try {

            var email = (String)session.getAttribute(Parameter.EMAIL_PARAMETER);

            userService.changePassword(email,newPassword,repeatPassword);
            return new ModelAndView(Path.HOME_PATH);
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView(Path.CHANGE_PASSWORD_PATH);
            modelAndView.addObject(Parameter.MESSAGE_ATTRIBUTE,e.getMessage());
            return modelAndView;
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public String logout(HttpSession session,
                            HttpServletRequest request,
                                        HttpServletResponse resp) {

        session.invalidate();
        Cookie[] cookies = request.getCookies();
        Cookie cookieByName = CookieUtil.getCookieByName(cookies, Parameter.REMEMBER_COOKIE);

        if (cookieByName != null){
            cookieByName.setMaxAge(0);
            resp.addCookie(cookieByName);
        }
        return Path.INDEX_PATH;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView start(HttpServletRequest req,
                              HttpServletResponse resp,
                              HttpSession session) {

        String encodedString = CookieUtil.getCookieValueByName(req.getCookies(), Parameter.REMEMBER_COOKIE);

        try {
            if (encodedString != null){
                String decrypt = AESManager.decrypt(encodedString);
                String email = decrypt.split(":")[0];
                String password = decrypt.split(":")[1];

                userService.login(email,password);

                Cookie cookie = new Cookie(Parameter.REMEMBER_COOKIE, AESManager.encrypt(email + ":" + password));
                cookie.setMaxAge(360000);
                resp.addCookie(cookie);

                session.setAttribute(Parameter.EMAIL_PARAMETER, email);
                return new ModelAndView(Path.HOME_PATH);
            }else {
                return new ModelAndView(Path.INDEX_PATH);
            }
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView(Path.INDEX_PATH);
            modelAndView.addObject(Parameter.MESSAGE_ATTRIBUTE, e.getMessage());
            return modelAndView;
        }
    }
}
