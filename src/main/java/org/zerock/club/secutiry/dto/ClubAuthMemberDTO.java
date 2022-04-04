package org.zerock.club.secutiry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Log4j2
@Getter
@Setter
@ToString
public class ClubAuthMemberDTO extends User {

    private String email;

    private String name;

    private boolean fromSocial;

    public ClubAuthMemberDTO(String username, String password, boolean fromSocial, Collection<? extends GrantedAuthority> authorities){
        //User 클래스의 생성자를 호출한다.
        super(username, password, authorities);

        this.email=username;
        this.fromSocial=fromSocial;
        //password는 부모 클래스를 사용하므로 별도의 멤버 변수로 선언하지 않았습니다.
    }

}