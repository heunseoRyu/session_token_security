package com.yours.members.service;

import com.yours.members.domain.Member;
import com.yours.members.dto.AddMemberRequest;
import com.yours.members.dto.AddMemberResponse;
import com.yours.members.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public AddMemberResponse addMember(AddMemberRequest request) {
        Member member = new Member();
        member.setUsername(request.getUsername());
        member.setEmail(request.getEmail());
        member.setPassword(passwordEncoder.encode(request.getPassword()));

        return new AddMemberResponse(memberRepository.save(member));
    }

    public Member getMember(String username) {
        return memberRepository.findByUsername(username)
                .orElse(null);
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElse(null);
    }
}
