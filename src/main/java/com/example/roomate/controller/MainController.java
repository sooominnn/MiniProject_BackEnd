package com.example.roomate.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.roomate.dto.request.CommentRequestDto;
import com.example.roomate.dto.response.ResponseDto;
import com.example.roomate.service.CommentService;
import com.example.roomate.service.MainService;
import com.example.roomate.service.PostService;
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
public class MainController {

    private final MainService mainService;

//    private final PostService postService;

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public ResponseDto<?> getMainPage(){
        return mainService.getAllPost();
    }
}
