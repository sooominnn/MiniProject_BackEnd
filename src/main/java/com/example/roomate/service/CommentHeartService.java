package com.example.roomate.service;

import com.example.roomate.dto.request.HeartRequestDto;
import com.example.roomate.dto.response.ResponseDto;
import com.example.roomate.entity.Comment;
import com.example.roomate.entity.CommentHeart;
import com.example.roomate.entity.Member;
import com.example.roomate.jwt.TokenProvider;
import com.example.roomate.repository.CommentHeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentHeartService {

    private  final CommentService commentService;

    private final CommentHeartRepository commentHeartRepository;

    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> createCommentHeart(HeartRequestDto requestDto, HttpServletRequest request) {
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

        Comment comment = commentService.isPresentComment(requestDto.getCommentId());
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }
        CommentHeart checkHeart = commentHeartRepository.findByCommentIdAndMemberId(comment.getId(), member.getId());
        if ( null != checkHeart ) {
            commentHeartRepository.delete(checkHeart);
            return  ResponseDto.success( "좋아요 취소");
        }


        CommentHeart commentheart = CommentHeart.builder()
                .member(member)
                .comment(comment)
                .build();
        commentHeartRepository.save(commentheart);
        return ResponseDto.success("댓글 좋아요 완료");

    }
    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.tokenValidation(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

}
