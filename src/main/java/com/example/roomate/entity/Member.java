package com.example.roomate.entity;


import com.example.roomate.dto.request.LoginRequestDto;
import com.example.roomate.dto.request.SignupRequestDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String member;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    public Member () {}

    public Member(SignupRequestDto SignupRequestDto) {
        this.member = SignupRequestDto.getMember();
        this.nickname = SignupRequestDto.getNickname();
        this.password = SignupRequestDto.getPassword();
    }

    public Member(LoginRequestDto loginRequestDto) {
        this.member = loginRequestDto.getMember();
        this.password = loginRequestDto.getPassword();

    }

    public Member(String member, String encodedPassword, String nickname) {
        this.member = member;
        this.password = encodedPassword;
        this.nickname = nickname;
    }
}
