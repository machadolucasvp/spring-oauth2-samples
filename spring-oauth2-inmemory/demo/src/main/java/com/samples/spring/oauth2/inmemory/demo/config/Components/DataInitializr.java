package com.samples.spring.oauth2.inmemory.demo.config.Components;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.samples.spring.oauth2.inmemory.demo.models.Role;
import com.samples.spring.oauth2.inmemory.demo.models.User;
import com.samples.spring.oauth2.inmemory.demo.repositories.UserRepository;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializr implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            createUser("Admin", "lucasvufma@gmail.com", passwordEncoder.encode("123"),"ROLE_USER");
            createUser("Cliente", "cliente@gmail.com", passwordEncoder.encode("123"),"ROLE_ADMIN");
        }

    }

    public void createUser(String name, String email, String password, String roleName) {

        Role role = new Role();
        role.setRole(roleName);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = new User(name, email, password,roles);
        userRepository.save(user);
    }

}