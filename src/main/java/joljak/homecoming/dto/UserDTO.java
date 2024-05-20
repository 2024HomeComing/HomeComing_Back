package joljak.homecoming.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String role;
    private String name;
    private String username; // 서버에서 만들어줄 유저네임 값
}
