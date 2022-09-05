package com.example.roomate.service;


import com.example.roomate.dto.request.HeartRequestDto;
import com.example.roomate.dto.response.ResponseDto;
import com.example.roomate.entity.Comment;
import com.example.roomate.entity.Member;
import com.example.roomate.entity.Post;
import com.example.roomate.jwt.TokenProvider;
import com.example.roomate.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class HeartService {

    private  final PostService postService;
    private final CommentService commentService;
    private final HeartRepository heartRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> heart (HeartRequestDto requestDto, HttpServletRequest request){
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

        String validation = "";

        if (null != requestDto.getPostId()) {
            validation = "Post";
        }
        else if (null != requestDto.getCommentId()) {
            validation = "Comment";
        }


        switch(validation){
            case "Post" :
                Post post = postService.isPresentPost(requestDto.getPostId());
                if (null == post) {
                    return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
                }
              break;
            case "Comment" :
                Comment comment = commentService.isPresentComment(requestDto.getCommentId());
                if (null == comment) {
                    return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
                }
                break;
            default:
                break;
        }








//        @Transactional
//        public ResponseDto<?> createPostHeart (HeartRequestDto requestDto, HttpServletRequest request){
//        if (null == request.getHeader("Refresh-Token")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//
//        if (null == request.getHeader("Authorization")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//
        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(requestDto.getId());
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        PostHeart findPostHeart = heartRepository.findByPostIdAndMemberId(post.getId(), member.getId());
        if (null != findPostHeart) {
            heartRepository.delete(findPostHeart);
            return ResponseDto.success("좋아요 취소");
        }


        PostHeart postheart = PostHeart.builder()
                .member(member)
                .post(post)
                .build();
        heartRepository.save(postheart);
        return ResponseDto.success("게시글 좋아요 완료");


    }
        @Transactional
        public Member validateMember (HttpServletRequest request){
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

}

