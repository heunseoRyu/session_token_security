package com.yours.members.dto;

import lombok.Data;

@Data
public class CreateAccessTokenByRefreshToken {
    private String refreshToken;
}
