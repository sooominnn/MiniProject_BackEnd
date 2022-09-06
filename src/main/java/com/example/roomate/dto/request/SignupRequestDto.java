package com.example.roomate.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String Member;
    private String nickname;
    private String password;
}
