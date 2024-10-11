package com.bgSPMall.shop.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@RequiredArgsConstructor
//@Service
//public class MyUserDetailsService implements UserDetailsService {
//
//    private final MemberRepository memberRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 위에 작성 되어 있는 String username 파라미터는 유저가 로그인 시 보낸 username임
//        // DB에서 username을 가진 유저를 찾아서 return new User(아이디,비번,권한) 하면됨
//
//        memberRepository.findByUsername(username);
//
//        return null;
//    }
//
//}