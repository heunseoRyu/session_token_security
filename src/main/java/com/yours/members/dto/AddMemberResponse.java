package com.yours.members.dto;

import com.yours.members.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddMemberResponse {
    private Long id;
    private String username;

    public  AddMemberResponse(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
    }
}
