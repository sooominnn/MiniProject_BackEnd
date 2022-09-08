package com.example.roomate.repository;

import com.example.roomate.entity.Member;
import com.example.roomate.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
//    List<Post> findAllByOrderByModifiedAtDesc();
//    List<Post> findAllByMember(Member member);
}
