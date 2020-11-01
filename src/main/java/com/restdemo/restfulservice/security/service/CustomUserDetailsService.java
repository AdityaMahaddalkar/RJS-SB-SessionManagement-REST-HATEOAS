package com.restdemo.restfulservice.security.service;

import com.restdemo.restfulservice.security.entity.User;
import com.restdemo.restfulservice.security.exception.UserNotFoundException;
import com.restdemo.restfulservice.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return new CustomUserDetails(username,
                user.getPassword(),
                user.getEmail(),
                Arrays.stream(user.getRole().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
    }
}
