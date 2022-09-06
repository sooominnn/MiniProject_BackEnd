package com.example.roomate.repository;

import com.example.roomate.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMember (String member);
//    List<Member> findAllByMember (String member);
}
