package com.example.roomate.repository;



import com.example.roomate.entity.Member;
import com.example.roomate.entity.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {
    Long countAllByPostId(Long postId);

    Boolean searchPostHeart(Long postId, Long memberId);
    PostHeart findByPostIdAndMemberId(Long postId, Long memberId);
    List<PostHeart> findAllByMember(Member member);
}
