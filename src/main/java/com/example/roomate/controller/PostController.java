package com.example.roomate.controller;


import com.example.roomate.dto.response.ResponseDto;
import com.example.roomate.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

//    @RequestMapping(value = "/post", method = RequestMethod.GET)
//    public ResponseDto<?> getAllPosts() {
//        return postService.getAllPost();
//    }



}
