package com.example.demo.Service;

import com.example.demo.Models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUser();

    User saveUser(User user);
    void deleteUser(long id);

    void updateUser(User user, User newUser);
    Optional<User> findUserById(long id);
}
