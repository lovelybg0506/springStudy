package com.bgSPMall.shop;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 페이징처리할때 css안먹어서 추가함
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // csrf 켜는 함수 + html의 form 태그에 <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}">  추가해야함.
    //                      그리고 나중에 Ajax 쓸때에도 위처럼 token 보내줘야함.
//    @Bean
//    public CsrfTokenRepository csrfTokenRepository() {
//        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//        repository.setHeaderName("X-XSRF-TOKEN");
//        return repository;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());    // csrf 공격을 막지않겠다 (csrf : 다른사이트에서 내 사이트에 접근가능) (개발중이라 끔)
//        http.csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository()) // csrf 공격을 막겠다
//                .ignoringRequestMatchers("/login")
//        );

        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/**").permitAll() // 특정페이지 로그인 검사 할지 결정 (permitall : 항상허용)
        );

        http.formLogin((formLogin)  // form으로 login할 것
                -> formLogin.loginPage("/login")    // login을 위한 폼의 url
                .defaultSuccessUrl("/")     // 성공시 url
                //.failureUrl("/fail")    // 실패시 url (안적으면 /login?error 로 이동
        );

        http.logout(logout -> logout.logoutUrl("/logout")); // logout url로 get요청하면 로그아웃됨

        return http.build();
    }

}