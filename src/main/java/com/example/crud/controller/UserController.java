package com.example.crud.controller;


import com.example.crud.entity.User;
import com.example.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("")
    public User insertUser(@RequestBody User user) {

        return userService.insertUser(user);
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getALlUsers();
    }

    @GetMapping("/{userId}")
    public User getUserByUserId (@PathVariable String userId) {

        return userService.getUserByUserId(userId);
    }

    @PutMapping("/{userId}")
    public void updateUserPw(@PathVariable String userId, @RequestBody User user) {
        userService.updateUserPw(userId, user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }
}
