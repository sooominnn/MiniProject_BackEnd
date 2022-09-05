package com.example.roomate.repository;

import com.example.roomate.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HeartRepository extends JpaRepository<Heart, Long> {
    Long countAllByCommentId(Long commentId);

    Heart findByCommentIdAndMemberId(Long commentId, Long memberId);

    Long countAllByPostId(Long postId);

    Heart findByPostIdAndMemberId(Long postId, Long memberId);
}