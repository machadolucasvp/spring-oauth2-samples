package com.samples.oauth2.jdbc.demo.controllers;

import java.util.List;

import com.samples.oauth2.jdbc.demo.models.User;
import com.samples.oauth2.jdbc.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController{

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public List<User> findAll(){
        return userRepository.findAll();
    }

}