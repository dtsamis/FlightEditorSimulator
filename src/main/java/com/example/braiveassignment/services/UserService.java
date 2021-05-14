package com.example.braiveassignment.services;

import com.example.braiveassignment.Model.Authority;
import com.example.braiveassignment.Model.User;
import com.example.braiveassignment.repositories.AuthorityRepository;
import com.example.braiveassignment.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository=authorityRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public void createUser(String username,String password)
    {
        Authority user=authorityRepository.save(Authority.builder().role("ROLE_USER").build());
        userRepository.save(User.builder().username(username).password(passwordEncoder.encode(password)).authority(user).build());
    }

    public Long countUsers()
    {
        return userRepository.count();
    }

}
