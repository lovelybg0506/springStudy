package com.bgSPMall.shop.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
}
