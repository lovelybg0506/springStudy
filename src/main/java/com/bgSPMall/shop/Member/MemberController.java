package com.bgSPMall.shop.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 화면 이동
    @GetMapping("/register")
    String register() {
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
}
