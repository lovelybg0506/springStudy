package com.bgSPMall.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());    // csrf 공격을 막지않겠다 (csrf : 다른사이트에서 내 사이트에 접근가능) (개발중이라 끔)
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/**").permitAll() // 특정페이지 로그인 검사 할지 결정 (permitall : 항상허용)
        );
        http.formLogin((formLogin)  // form으로 login할 것
                -> formLogin.loginPage("/login")    // login을 위한 폼의 url
                .defaultSuccessUrl("/")     // 성공시 url
                //.failureUrl("/fail")    // 실패시 url (안적으면 /login?error 로 이동
        );

        return http.build();
    }
}