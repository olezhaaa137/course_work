package com.example.course_work.web;

import com.example.course_work.entities.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @ModelAttribute
    public User user(@AuthenticationPrincipal User user){
        return user;
    }
    @GetMapping
    public String homePage(){
        return "home";
    }

}
