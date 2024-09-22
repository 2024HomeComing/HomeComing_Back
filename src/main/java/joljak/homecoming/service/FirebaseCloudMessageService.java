package joljak.homecoming.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import joljak.homecoming.dto.FcmMessageRequestDto;
import org.springframework.stereotype.Service;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
public class FirebaseCloudMessageService {

    public static void sendNotification(FcmMessageRequestDto fcmMessageRequestDto) {
        // 메시지 생성
        Message message = Message.builder()
                .putData("title", fcmMessageRequestDto.getTitle())
                .putData("body", fcmMessageRequestDto.getMessage())
                .putData("userId", fcmMessageRequestDto.getUserId()) // 데이터 추가
                .putData("reportId", fcmMessageRequestDto.getReportId())
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
