package com.yours.members.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yours.members.dto.TokenErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 필터에서 에러가 나면 그 앞에 필터(필터를 호출한 필터)에서 처리한다.throw된다.
@Component
public class TokenExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response); // 한 필터에서 에러가 터지면 이렇게 처리하는 방법도 있고, 이렇게 필터로 나누는 방법도 있음.
        } catch(JwtException ex){
            setErrorResponse(response,ex);
        }
    }

    public void setErrorResponse(HttpServletResponse response, Throwable ex) throws IOException{
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json; charset=utf-8");

        TokenErrorResponse jwtExceptionResponse = new TokenErrorResponse(ex.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(jwtExceptionResponse));
    }
}
