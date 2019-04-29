package com.gmail.etauroginskaya.springbootmodule.week4.controller;

import com.gmail.etauroginskaya.week4.service.UserService;
import com.gmail.etauroginskaya.week4.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    private  final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users*")
    public String getUsers(Model model) {
        List<UserDTO> users = userService.getUsers();
        model.addAttribute("users", users);
        return "/users";
    }
}
