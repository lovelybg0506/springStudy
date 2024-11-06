package com.bgSPMall.shop.Member;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component // 필터 등록
public class JwtFilter extends OncePerRequestFilter { // OncePerRequestFilter = 요청마다 1회만 실행됨

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies(); // 1.누가 쿠키를 제출하면,

        if (cookies == null){
            filterChain.doFilter(request, response); // 다음필터 실행해라.
            return;
        }

        var jwtCookie = "";

        // 쿠키는 여러개 전달될 수 있어서 정확히 꺼내야함(for문 사용)
        for(int i=0; i<cookies.length; i++) {
            if(cookies[i].getName().equals("jwt")) { // 2.jwt라는 이름의 쿠키를 꺼내고
                jwtCookie = cookies[i].getValue();
            }
        }

        Claims claim;
        try{
            claim = JwtUtil.extractToken(jwtCookie);    // 3.jwt값이 정상이면,
        } catch (Exception e){
            System.out.println("유효기간이 만료되거나 문제가 있음.");
            filterChain.doFilter(request, response);
            return;
        }
//        claim.get("username").toString(); // JWT 내용

        // 4. auth 변수에 넣어라.
        var arr = claim.get("authorities").toString().split(","); // split : ,콤마 로 구분해서 나열
        var authorities = Arrays.stream(arr).map(a -> new SimpleGrantedAuthority(a)).toList(); // array안의 자료를 simple~ 로 변환

        var customUser = new CustomUser(claim.get("username").toString(),"none",authorities);
        customUser.userName = claim.get("displayName").toString();

        var authToken = new UsernamePasswordAuthenticationToken(
                customUser,    // claim.get("username").toString() <- principal
                null
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // auth변수에 details부분
        SecurityContextHolder.getContext().setAuthentication(authToken);


        // 요청들어올때마다 실행되는 코드
        filterChain.doFilter(request, response);
    }

}
