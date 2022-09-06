package com.example.roomate.controller;

import com.example.roomate.dto.GlobalResDto;
import com.example.roomate.dto.request.LoginRequestDto;
import com.example.roomate.dto.request.SignupRequestDto;
import com.example.roomate.jwt.TokenProvider;
import com.example.roomate.security.user.UserDetailsImpl;
import com.example.roomate.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public GlobalResDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return memberService.signup(signupRequestDto);
//        return "redirect:/member/login"
    }

    @PostMapping("/login")
    public GlobalResDto login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return memberService.login(loginRequestDto, response);
    }

    @GetMapping("/issue/token")
    public GlobalResDto issuedToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        response.addHeader(TokenProvider.ACCESS_TOKEN, tokenProvider.createToken(userDetails.getMember().getMember(), "Access"));
        return new GlobalResDto("issuedToken Success", HttpStatus.OK.value());
    }
}
