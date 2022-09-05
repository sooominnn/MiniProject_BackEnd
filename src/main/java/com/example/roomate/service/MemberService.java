package com.example.roomate.service;

import com.example.roomate.dto.GlobalResDto;
import com.example.roomate.dto.request.LoginRequestDto;
import com.example.roomate.dto.request.SignupRequestDto;
import com.example.roomate.entity.Member;
import com.example.roomate.entity.RefreshToken;
import com.example.roomate.jwt.TokenDto;
import com.example.roomate.jwt.TokenProvider;
import com.example.roomate.repository.MemberRepository;
import com.example.roomate.repository.RefreshTokenRepository;
import com.example.roomate.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
public class MemberService {

//    GlobalResDto signup(SignupRequestDto signupRequestDto);
//    GlobalResDto login(LoginRequestDto loginRequestDto, HttpServletResponse response);

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

//    @Override
    @Transactional
    public GlobalResDto signup(SignupRequestDto signupRequestDto) {
        // userId 중복 검사
        if (memberRepository.findByMember(signupRequestDto.getMember()).isPresent()) {
            throw new RuntimeException("SignUp Fail Cause Overlap");
        }

        signupRequestDto.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        com.example.roomate.entity.Member member = new com.example.roomate.entity.Member(signupRequestDto);
        memberRepository.save(member);

        return new GlobalResDto("회원가입을 축하합니다.", HttpStatus.OK.value());
    }

//    @Override
    @Transactional
    public GlobalResDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        Member member = memberRepository.findByMember(loginRequestDto.getMember()).orElseThrow(
                ()->new RuntimeException("회원을 찾을 수 없습니다.")
        );

        if(!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 토큰 발급
        TokenDto tokenDto = tokenProvider.createAllToken(member.getMember());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMember(member.getMember());

        if(refreshToken.isPresent()) {
            refreshToken.get().setRefreshToken(tokenDto.getRefreshToken());
            refreshTokenRepository.save(refreshToken.get());
        }else{
            RefreshToken newRefreshToken = new RefreshToken(tokenDto.getRefreshToken(), member.getMember());
            refreshTokenRepository.save(newRefreshToken);
        }

        setTokenOnHeader(response, tokenDto);

        return new GlobalResDto("로그인에 성공하였습니다.", HttpStatus.OK.value());
    }

    private void setTokenOnHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(TokenProvider.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(TokenProvider.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }



}
