package com.example.demo.Service.impl;

import com.example.demo.Models.User;
import com.example.demo.Repo.UserRepo;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<User> getAllUser(){
        return (List<User>) userRepo.findAll();
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }

    @Override
    public void updateUser(User user, User newUser) {

        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());

        saveUser(user);
    }

    @Override
    public Optional<User> findUserById(long id) {
        return userRepo.findById(id);
    }
}
