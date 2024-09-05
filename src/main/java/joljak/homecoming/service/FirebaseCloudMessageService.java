package joljak.homecoming.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import joljak.homecoming.dto.FcmMessageRequestDto;
import org.springframework.stereotype.Service;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
public class FirebaseCloudMessageService {

    public static void sendNotification(FcmMessageRequestDto fcmMessageRequestDto) {
        Message message = Message.builder()
                .putData("title", fcmMessageRequestDto.getTitle())
                .putData("message", fcmMessageRequestDto.getMessage())
                .setToken(fcmMessageRequestDto.getTargetToken())
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (FirebaseMessagingException e) {
            log.error(e.getMessage());
        }
    }
}
