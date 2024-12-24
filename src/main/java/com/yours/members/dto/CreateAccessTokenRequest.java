package com.yours.members.dto;

import lombok.Data;

@Data
public class CreateAccessTokenRequest {
    private String email;
    private String password;
}
