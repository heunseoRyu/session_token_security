package com.yours.members.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateAccessTokenResponse {
    private String result;
    private String token;
    private String refreshToken;
}
