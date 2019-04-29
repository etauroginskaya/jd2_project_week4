package com.gmail.etauroginskaya.springbootmodule.week4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class DefaultController {

    @GetMapping("/login")
    public String getLoginPage(){
        return "/login";
    }

    @GetMapping("/")
    public String getHomePage(){
        return "/login";
    }

    @GetMapping("/about")
    public String getAboutPage(){
        return "/about";
    }

    @GetMapping("/403")
    public ModelAndView accesssDenied(Principal user) {
        ModelAndView model = new ModelAndView();
        if (user != null) {
            model.addObject("msg", "Hi " + user.getName()
                    + ", you do not have permission to access this page!");
        } else {
            model.addObject("msg",
                    "You do not have permission to access this page!");
        }
        model.setViewName("/error/403");
        return model;
    }
}
