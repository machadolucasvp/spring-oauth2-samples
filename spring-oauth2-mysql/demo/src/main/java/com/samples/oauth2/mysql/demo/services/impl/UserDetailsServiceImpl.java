package com.samples.oauth2.mysql.demo.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.samples.oauth2.mysql.demo.models.User;
import com.samples.oauth2.mysql.demo.repositories.UserRepository;

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

            return buildUserDetails(user.get());
    }

    private UserDetails buildUserDetails(User user) {
        List<GrantedAuthority> authorities = CollectionUtils.isEmpty(user.getRole()) ? Collections.emptyList()
                : user.getRole().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
                        .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), "", authorities);
        
    }

}