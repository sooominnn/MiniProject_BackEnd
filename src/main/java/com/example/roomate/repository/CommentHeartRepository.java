package com.example.roomate.repository;

import com.example.roomate.entity.CommentHeart;
import com.example.roomate.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {
    Long countAllByCommentId(Long commentId);
    CommentHeart findByCommentIdAndMemberId(Long commentId, Long memberId);
    List<CommentHeart> findAllByMember(Member member);
}
