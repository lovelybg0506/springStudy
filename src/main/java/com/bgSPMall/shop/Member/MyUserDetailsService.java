package com.bgSPMall.shop.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 위에 작성 되어 있는 String username 파라미터는 유저가 로그인 시 보낸 username임
        // DB에서 username을 가진 유저를 찾아서 return new User(아이디,비번,권한) 하면됨
        // username == id (Spring Security 라이브러리 쓰면 무조건 username으로 써야해서..)

        var result = memberRepository.findByUserId(username);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("ID Not Found Error");
        }

        var user = result.get();
        List<GrantedAuthority> authorities = new ArrayList<>(); // 권한

        // 메모
        if (user.getUserId().equals("admin")) {
            authorities.add(new SimpleGrantedAuthority("Admin")); // 아이디가 admin일 경우 Admin으로 메모
        } else {
            authorities.add(new SimpleGrantedAuthority("NormalUser")); // 외 NormalUser 메모
        }

        return new User(user.getUserId(), user.getUserPwd(), authorities);
    }

}