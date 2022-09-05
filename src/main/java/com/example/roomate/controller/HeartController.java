package com.example.roomate.controller;


import com.example.roomate.dto.request.HeartRequestDto;
import com.example.roomate.dto.response.ResponseDto;
import com.example.roomate.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Validated
@RequiredArgsConstructor
@RestController
public class HeartController {

    private final HeartService heartService;

    @RequestMapping(value = "//heart/{id}", method = RequestMethod.POST)
    public ResponseDto<?> createHeart(@RequestBody HeartRequestDto requestDto,
                                          HttpServletRequest request) {
        return heartService.createHeart(requestDto, request);
    }
//    @RequestMapping(value = "/api/auth/postComment/heart", method = RequestMethod.POST)
//    public ResponseDto<?> createPostCommentHeart(@RequestBody HeartRequestDto requestDto,
//                                                 HttpServletRequest request) {
//        return postCommentHeartService.createPostCommentHeart(requestDto, request);
//    }

}