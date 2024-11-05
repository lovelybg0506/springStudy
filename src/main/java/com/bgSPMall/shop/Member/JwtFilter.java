package com.bgSPMall.shop.Member;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // 필터 등록
public class JwtFilter extends OncePerRequestFilter { // OncePerRequestFilter = 요청마다 1회만 실행됨

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies(); // 쿠키는 여러개 전달될 수 있어서 정확히 꺼내야함(for문 사용)

        if (cookies == null){
            filterChain.doFilter(request, response); // 다음필터 실행해라.
            return;
        }

        var jwtCookie = "";

        for(int i=0; i<cookies.length; i++) {
            if(cookies[i].getName().equals("jwt")) {
                jwtCookie = cookies[i].getValue();
            }
        }

        Claims claim;
        try{
            claim = JwtUtil.extractToken(jwtCookie);
        } catch (Exception e){
            filterChain.doFilter(request, response);
            return;
        }
//        claim.get("username").toString(); // JWT 내용

        var authToken = new UsernamePasswordAuthenticationToken(
                claim.get("username").toString(), ""
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);


        // 요청들어올때마다 실행되는 코드
        filterChain.doFilter(request, response);
    }

}
