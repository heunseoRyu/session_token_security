package com.yours.members.controller;

import com.yours.members.dto.CreateAccessTokenByRefreshToken;
import com.yours.members.dto.CreateAccessTokenRequest;
import com.yours.members.dto.CreateAccessTokenResponse;
import com.yours.members.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ApiController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<CreateAccessTokenResponse> login(
            @RequestBody CreateAccessTokenRequest request
    ){
        CreateAccessTokenResponse response = tokenService.getAccessToken(request);
        if(response !=null){
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login/token")
    public ResponseEntity<CreateAccessTokenResponse> tokenLogin(
            @RequestBody CreateAccessTokenByRefreshToken request
    ){
        CreateAccessTokenResponse response = tokenService.refreshAccessToken(request);
        if(response !=null){
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok().body("test");
    }

}
