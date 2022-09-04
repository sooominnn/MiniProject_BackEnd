package com.example.roomate.controller;


import javax.servlet.http.HttpServletRequest;

import com.example.roomate.dto.request.CommentRequestDto;
import com.example.roomate.dto.response.ResponseDto;
import com.example.roomate.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @RequestMapping(value = "/comment/{id}", method = RequestMethod.POST)
    public ResponseDto<?> createComment(@RequestBody CommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return commentService.createComment(requestDto, request);
    }

    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getAllComments(@PathVariable Long postId) {
        return commentService.getAllCommentsByPost(postId);
    }

    @RequestMapping(value = "/comment/{id}", method = RequestMethod.PUT)
    public ResponseDto<?> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return commentService.updateComment(commentId, requestDto, request);
    }

    @RequestMapping(value = "/comment/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteComment(@PathVariable Long commentId,
                                        HttpServletRequest request) {
        return commentService.deleteComment(commentId, request);
    }
}