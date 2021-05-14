package com.example.braiveassignment.controllers;

import com.example.braiveassignment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAPIController {
    @Autowired
    UserService userService;

    @PostMapping("/api/register")
    public void createUser(@RequestParam("username") String username, @RequestParam("password")String password)
    {
        userService.createUser(username,password);
        System.out.println("Number of Users" +userService.countUsers());
    }

}
