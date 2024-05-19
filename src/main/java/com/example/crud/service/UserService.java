package com.example.crud.service;

import com.example.crud.entity.User;
import com.example.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User insertUser(User user) {

        return userRepository.save(user);
    }

    public List<User> getALlUsers() {
        return userRepository.findAll();
    }

    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public void updateUserPw(String userId, User user) {
        userRepository.updateUserByUserId(userId, user.getUserPw());
    }

    public void deleteUser(String userId) {
        userRepository.deleteUserByUserId(userId);
    }
}
