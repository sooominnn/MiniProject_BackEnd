package com.example.roomate.repository;


import java.util.List;

import com.example.roomate.entity.Comment;
import com.example.roomate.entity.Member;
import com.example.roomate.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByMember(Member member);
}