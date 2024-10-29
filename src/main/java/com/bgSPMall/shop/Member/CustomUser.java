package com.bgSPMall.shop.Member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

    // User 클래스를 상속받아서 커스텀해서 사용 (User클래스에서는 id,pwd,권한 정도밖에 쓸 수 없는데, 유저이름을 가져오고 싶어서)
public class CustomUser extends User {

    public String userName; // 위 loadUserByUsername()함수에서 return값 넘길때 userInfo.userName값 추가해서 넘겨줌

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}

