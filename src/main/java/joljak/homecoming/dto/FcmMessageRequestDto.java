package joljak.homecoming.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FcmMessageRequestDto {

    private String userId;

    private String title;

    private String message;

    private String targetToken; //FCM 토큰

    public FcmMessageRequestDto(String title, String message, String targetToken) {
        this.title = title;
        this.message = message;
        this.targetToken = targetToken;
    }
}
