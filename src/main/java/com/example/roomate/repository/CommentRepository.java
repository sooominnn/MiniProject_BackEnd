package com.example.roomate.repository;


import com.example.roomate.entity.Comment;
import com.example.roomate.entity.Member;
import com.example.roomate.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);
//    List<Comment> findAllByPostAndComment(Post post, Comment parent);
//    List<Comment> findAllByMember(Member member);
}
