package com.yours.members.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) //unique니 중복체크 해줘야 한다. -> controller / DataIntegrityViolationException
    private String username;

    @Column(nullable = false) //사용자로 부터 받는건 nullable false
    private String email;

    @Column(nullable = false)
    private String password;
}
