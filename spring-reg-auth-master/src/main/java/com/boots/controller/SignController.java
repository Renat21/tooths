package com.boots.controller;

import com.boots.entity.Sign;
import com.boots.entity.User;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class SignController {

    @Autowired
    private UserService userService;

    @GetMapping("/sign")
    public String registration(Model model) {
        model.addAttribute("signForm", new Sign());
        return "sign";
    }

    @PostMapping("/sign")
    public String addUser(@ModelAttribute("signForm") Sign signForm) {
        userService.saveSign(signForm);
        return "redirect:/";
    }
}
