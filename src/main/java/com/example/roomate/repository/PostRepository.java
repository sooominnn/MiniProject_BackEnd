package com.example.roomate.repository;


import java.util.List;
import java.util.Optional;

import com.example.roomate.entity.Member;
import com.example.roomate.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();
    List<Post> findAllByMember(Member member);
}
