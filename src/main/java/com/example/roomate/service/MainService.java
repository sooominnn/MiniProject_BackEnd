package com.example.roomate.service;


import com.example.roomate.dto.response.GetAllPostResponseDto;
import com.example.roomate.dto.response.MainResponseDto;
import com.example.roomate.dto.response.PostResponseDto;
import com.example.roomate.dto.response.ResponseDto;
import com.example.roomate.entity.*;
import com.example.roomate.jwt.TokenProvider;
import com.example.roomate.repository.PostHeartRepository;
import com.example.roomate.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MainService {

    private final Post post;
    private final PostRepository postRepository;
    private final PostHeartRepository postHeartRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> getMainPage(HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        // 게시글, heartOn 추가해야함.
        List<Post> postList = postRepository.findAllByMember(member);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        for (Post post : postList) {
            postResponseDtoList.add(
                    PostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .heartNum(postHeartRepository.countAllByPostId(post.getId()))
                            .build()
            );
        }
        return ResponseDto.success(
                MainResponseDto.builder()
                        .posts(postResponseDtoList)
                        .build()
        );
    }
    Long heartNum = postHeartRepository.countAllByPostId(post.getId());

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllPost() {

        List<Post> allPosts = postRepository.findAllByOrderByModifiedAtDesc();
        List<GetAllPostResponseDto> getAllPostResponseDtoList = new ArrayList<>();

        for (Post post : allPosts) {
            getAllPostResponseDtoList.add(
                    GetAllPostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .member(post.getMember().getNickname())
                            .heartOn(postHeartRepository.searchPostHeart(post.getId(), member.getId())
                            .heartNum(postHeartRepository.countAllByPostId(post.getId()))
                            .build()
            );
        }
        return ResponseDto.success(getAllPostResponseDtoList);
    }
    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.tokenValidation(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
