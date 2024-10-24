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
import java.util.Collection;
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
            authorities.add(new SimpleGrantedAuthority("admin")); // 아이디가 admin일 경우 admin으로 메모
        } else {
            authorities.add(new SimpleGrantedAuthority("normalUser")); // 외 normalUser 메모
        }

        // MemberController.java 의 myPage 함수의 파라미터로 전달되는 값 == userInfo
        var userInfo =  new CustomUser(user.getUserId(), user.getUserPwd(), authorities);
        userInfo.userName = user.getUserName();
        return userInfo;
    }

}

// User 클래스를 상속받아서 커스텀해서 사용 (User클래스에서는 id,pwd,권한 정도밖에 쓸 수 없는데, 유저이름을 가져오고 싶어서)
class CustomUser extends User {

    public String userName; // 위 loadUserByUsername()함수에서 return값 넘길때 userInfo.userName값 추가해서 넘겨줌

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}