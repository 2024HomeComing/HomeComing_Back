package joljak.homecoming.controller;

import joljak.homecoming.dto.BoardDto;
import joljak.homecoming.dto.ProfileUpdateDto;
import joljak.homecoming.entity.User;
import joljak.homecoming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUSerById(@PathVariable String userId) {
        User user = userService.getUserProfile(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/profile_update")
    public ResponseEntity<User> updateUser(@RequestPart("P_update")ProfileUpdateDto profileUpdateDto, @RequestPart(value = "images", required = false) MultipartFile imageFile) throws IOException {
        User updatedUser = userService.updateUser(profileUpdateDto, imageFile);
        return ResponseEntity.ok().body(updatedUser);
    }
}
