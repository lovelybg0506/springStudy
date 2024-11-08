package com.bgSPMall.shop.Member;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // 회원가입 화면 이동
    @GetMapping("/register")
    String register(@AuthenticationPrincipal UserDetails userDetails) {

        // 로그인한 유저가 회원가입버튼 누르면 list페이지로 이동시킴
        if (userDetails != null) {
            return "redirect:/list";
        }

        return "register.html";
    }

    // 회원가입 유저 등록
    @PostMapping("/member")
    String addMember(String n_userId, String n_userPwd, String n_userName) {

        Member member = new Member();

        member.setUserId(n_userId);

//        var hashPwd = new BCryptPasswordEncoder().encode(n_userPwd);    // password hashing
        var hashPwd = passwordEncoder.encode(n_userPwd);    // 위 코드와 같은데 Bean으로 등록해서 사용
        member.setUserPwd(hashPwd);

        member.setUserName(n_userName);
        memberRepository.save(member);

        return "redirect:/";
    }

    // 로그인화면 이동
    @GetMapping("/login")
    String login() {

        var result = memberRepository.findByUserId("admin");

//        System.out.println(result.get().getId());
//        System.out.println(result.get().getUserId());
//        System.out.println(result.get().getUserPwd());
//        System.out.println(result.get().getUserName());
//        System.out.println(result.get().toString());    // Member.java class 위에 @ToString 작성해야 사용가능

        return "login.html";
    }

    // myPage
    @PreAuthorize("isAuthenticated()") // 로그인한 유저만 API 실행
    @GetMapping("/mypage")
    String myPage(@AuthenticationPrincipal CustomUser userInfo, Model model) {
//        System.out.println(auth); // 유저토큰정보
//        System.out.println(auth.getName()); // userId
//        System.out.println(auth.isAuthenticated()); // 현재 로그인여부 : true or false

        // Controller에서 현재유저의 권한 출력가능
//        System.out.println(auth.getAuthorities().contains(
//                new SimpleGrantedAuthority("NormalUser")
//        ));

        //System.out.println(userInfo.userName);
        model.addAttribute("userName", userInfo.userName);

        if (userInfo == null) {
            return "login.html";
        } else {
            return "mypage.html";
        }
    }

    @GetMapping("/user/1")
    @ResponseBody
    public MemberDto getUser() {
        var a = memberRepository.findById(1L);
        var result = a.get();
        var data = new MemberDto(result.getUserId(), result.getUserName());

        return data;
    }

    @PostMapping("/login/jwt")
    @ResponseBody
    public String loginJWT(@RequestBody Map<String, String> data,
                           HttpServletResponse response) {

        var authToken = new UsernamePasswordAuthenticationToken(data.get("username"), data.get("password"));
        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);   // JWT도 loadUserByUsername 셋팅 되어있어야 함
        SecurityContextHolder.getContext().setAuthentication(auth);
        //SecurityContextHolder.getContext().getAuthentication(); // auth변수

        var jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication()); // 계산기용, 변환기용 함수에만 static을 붙이는게 좋음 (재활용하는거라)
        System.out.println(jwt);

        var cookie = new Cookie("jwt", jwt); // cookie는 문자만 저장 가능
        cookie.setMaxAge(10); // 초단위의 유효기간 (JWT 유효기간이랑 비슷하거나 같게)
        cookie.setHttpOnly(true); // 쿠키를 자바스크립트로 조작하기 어려워짐
        cookie.setPath("/"); // 쿠키가 전송될 URL : '/'는 모든 url

        response.addCookie(cookie);

        return jwt;
    }

    @GetMapping("my-page/jwt")
    @ResponseBody
    String myPageJWT(Authentication auth) {

        var user = (CustomUser)auth.getPrincipal();
        System.out.println("user:"+user);
        System.out.println("user.userName:"+user.userName);
        System.out.println("user.id:"+user.id);
        System.out.println("user.getAuthorities:"+user.getAuthorities());

        return "mypagedata";
    }
}

// Data Transfer Object (DTO) : 데이터 변환용 클래스
class MemberDto {
    public String userId;
    public String userName;

    MemberDto(String id, String name){ // constructor (new memberDto() 할 때 실행됨)
        this.userId = id;
        this.userName = name;
    }
}