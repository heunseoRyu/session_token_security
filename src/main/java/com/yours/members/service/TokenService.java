package com.yours.members.service;

import com.yours.members.config.JwtProperties;
import com.yours.members.config.jwt.TokenProvider;
import com.yours.members.domain.Member;
import com.yours.members.domain.RefreshToken;
import com.yours.members.dto.CreateAccessTokenByRefreshToken;
import com.yours.members.dto.CreateAccessTokenRequest;
import com.yours.members.dto.CreateAccessTokenResponse;
import com.yours.members.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;
    private final TokenProvider tokenProvider;

    public CreateAccessTokenResponse getAccessToken(
            CreateAccessTokenRequest request
    ) {
        Member member = memberService.getMemberByEmail(request.getEmail());
        if(member!=null){
            if(passwordEncoder.matches(request.getPassword(),member.getPassword())){
                return createAccessToken(member, null);
            }
        }
        return null;
    }

    public CreateAccessTokenResponse createAccessToken(Member member, String refreshToken) {
        Duration tokenDuration = Duration.ofMinutes(jwtProperties.getDuration());
        Duration refreshDuration = Duration.ofMinutes(jwtProperties.getRefreshDuration());

        // refreshToken 검색
        RefreshToken savedRefreshToken = refreshTokenRepository.findByEmail(member.getEmail()).orElse(null);
        if(savedRefreshToken!=null && refreshToken!=null){
            if(!savedRefreshToken.getRefreshToken().equals(refreshToken)){
                return new CreateAccessTokenResponse("Invalid token.",null,null);
            }
        }
        String accessToken = tokenProvider.generateToken(member,tokenDuration,true);
        String newRefreshToken = tokenProvider.generateToken(member,refreshDuration,false);

        if(savedRefreshToken == null)
            savedRefreshToken = new RefreshToken(member.getEmail(),newRefreshToken);
        else
            savedRefreshToken.setRefreshToken(newRefreshToken);

        refreshTokenRepository.save(savedRefreshToken);
        return new CreateAccessTokenResponse("ok",accessToken,newRefreshToken);
    }

    public CreateAccessTokenResponse refreshAccessToken(CreateAccessTokenByRefreshToken request) {
        try{
            Claims claims = tokenProvider.getClaims(request.getRefreshToken());
            String type = claims.get("type").toString();
            if(type == null || !type.equals("R")){
                throw new Exception("Invalid token");
            }
            Member member = memberService.getMemberByEmail(claims.getSubject());
            return createAccessToken(member, request.getRefreshToken());
        }catch(ExpiredJwtException e){
            return new CreateAccessTokenResponse("만료된 토큰",null,null);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new CreateAccessTokenResponse(e.getMessage(),null,null);
        }
    }

}
