package com.yours.members.repository;

import com.yours.members.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //optional 로 반환되는건 unique 라는 뜻.
    Optional<Member> findByUsername(String username);

    Optional<Member> findByEmail(String email);
}
