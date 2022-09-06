package com.example.roomate.controller;


import javax.servlet.http.HttpServletRequest;

import com.example.roomate.dto.response.ResponseDto;
import com.example.roomate.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    //(value="file",required = false)
//    @RequestMapping(value = "/api/auth/post", method = RequestMethod.POST)
//    public ResponseDto<?> createPost(@RequestPart PostRequestDto requestDto, @RequestPart MultipartFile multipartFile,
//                                     HttpServletRequest request) {
//        return postService.createPost(requestDto, multipartFile, request);
//    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public ResponseDto<?> getAllPosts() {
        return postService.getAllPost();
    }



}
