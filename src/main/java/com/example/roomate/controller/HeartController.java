package com.example.roomate.controller;

import com.example.roomate.dto.request.HeartRequestDto;
import com.example.roomate.dto.response.ResponseDto;
import com.example.roomate.service.CommentHeartService;
import com.example.roomate.service.PostHeartService;
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

    private final PostHeartService postHeartService;
    private final CommentHeartService commentHeartService;

    @RequestMapping(value = "/heart/postId", method = RequestMethod.POST)
    public ResponseDto<?> createPostHeart(@RequestBody HeartRequestDto requestDto,
                                          HttpServletRequest request) {
        return postHeartService.createPostHeart(requestDto, request);
    }
    @RequestMapping(value = "/heart/commentId", method = RequestMethod.POST)
    public ResponseDto<?> createCommentHeart(@RequestBody HeartRequestDto requestDto,
                                             HttpServletRequest request) {
        return commentHeartService.createCommentHeart(requestDto, request);
    }

}
