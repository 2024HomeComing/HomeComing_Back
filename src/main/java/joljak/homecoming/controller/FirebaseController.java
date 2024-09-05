package joljak.homecoming.controller;

import joljak.homecoming.dto.FcmMessageRequestDto;
import joljak.homecoming.service.FirebaseCloudMessageService;
import joljak.homecoming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/FCM")
public class FirebaseController {

    @Autowired
    private UserService userService;

    @PostMapping("/send/{userId}")
    public ResponseEntity<String> sendNotification(@PathVariable String userId, @RequestBody FcmMessageRequestDto fcmMessageRequestDto) {
        String targetToken = userService.getFcmTokenByUserId(userId);
        if (targetToken != null) {
            fcmMessageRequestDto.setTargetToken(targetToken);
            FirebaseCloudMessageService.sendNotification(fcmMessageRequestDto);
            return ResponseEntity.ok("Notification sent successfully to user");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User's FCM token not found");
        }
    }


}
