package com.samples.spring.oauth2.inmemory.demo.services.Impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.samples.spring.oauth2.inmemory.demo.models.User;
import com.samples.spring.oauth2.inmemory.demo.models.CustomUserDetails;
import com.samples.spring.oauth2.inmemory.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(email);
        
        user.orElseThrow(
            () -> new UsernameNotFoundException("Email not found"));
            
            return new CustomUserDetails(user.get());
            
        }
    
        

}