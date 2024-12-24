package com.yours.members.config.jwt;

import com.yours.members.config.JwtProperties;
import com.yours.members.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TokenProvider {
    private final JwtProperties jwtProperties;
    private final SecretKey key;
    private final JwtParser parser;

    // 직접 주입해줘야 하는거라 @RequiredArgsConstructor 안씀
    public TokenProvider(JwtProperties jwtProperties){
        this.jwtProperties = jwtProperties;
        key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtProperties.getSecretKey()));
        parser = Jwts.parser().verifyWith(key).build();
    }

    public String generateToken(Member user, Duration expiredAt,boolean isAccessToken){
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiredAt.toMillis());

        // payload는 누구나 열어볼 수 있기 때문에 비번은 넣으면 안됨!
        return Jwts.builder()
                .header().add("type","JWT").add("alg","HS256").and()
                .claims()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiration(expiry)
                .subject(user.getEmail())
                .add("id",user.getId()) // custom
                .add("type",isAccessToken? "A":"R")
                .and()
                .signWith(key,Jwts.SIG.HS256)
                .compact();
    }

    // UsernamePasswordAuthentication(session로그인)을 흉내낸 토큰 필터를 만들어야함.
    public Authentication getAuthentication(String token) throws JwtException,IllegalArgumentException {
        Claims claims = getClaims(token);

        // refreshtoken은 인증용으로 사용할 수 없다.
        String type = claims.get("type").toString();
        if(type==null || claims.get("type").equals("A")) throw new IllegalArgumentException("");

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user")); // 권한 관리하면 user 를 찾아서 authority를 넣어줘야함.

        UserDetails userDetails = User
                .withUsername(claims.getSubject())
                .password("")
                .authorities(authorities)
                .build();

        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }

    public Claims getClaims(String token) throws JwtException,IllegalArgumentException {
        Jws<Claims> jws = parser.parseSignedClaims(token);
        return jws.getPayload();
    }

}
