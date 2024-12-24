package com.yours.members.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class AddMemberRequest {
    @NotEmpty(message = "username은 필수입니다.")
    private String username; //요청시 비어있으면 안됌

    @NotEmpty(message = "이메일은 필수입니다.")
    @Email //입력된 내용이 이메일 형식인가.
    private String email;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    @Size(min=3, max=30, message = "비밀번호는 3자~20자 입니다.")
    private String password;
}
