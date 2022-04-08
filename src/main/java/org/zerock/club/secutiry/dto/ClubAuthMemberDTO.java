package org.zerock.club.secutiry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@Setter
@ToString
public class ClubAuthMemberDTO extends User implements OAuth2User {

    //User 클래스는 UserDetailsService로부터 핵심 유저정보를 모델링합니다.
    //User 클래스를 상속하고 부모 클래스인 User클래스의 생성자를 호출할 수 있는 생성자를 만드빈다.
    //부모 클래스인 User클래스에 사용자 정의 생성자가 있으므로 반드시 호출할 필요가 있습니다.

    //ClubAuthMemberDTO는 DTO역할을 수행하는 클래스인 동시에 스프링 시큐리티에서 인증/인가 작업에 사용할 수 있습니다.
    //Password는 부모 클래스를 사용하므로 별도의 멤버 변수로는 사용하지 않습니다.



    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    private Map<String,Object> attr;

    public ClubAuthMemberDTO(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            boolean fromSocial) {

        super(username, password, authorities);
        this.fromSocial = fromSocial;
        this.email=username;
        this.password=password;

    }

    public ClubAuthMemberDTO(String username,
                             String password,
                             Collection<? extends GrantedAuthority> authorities,
                             boolean fromSocial,
                             Map<String, Object> attr) {

       this(username, password, authorities,fromSocial);
        this.attr = attr;
    }
    @Override
    public Map<String,Object> getAttributes(){
        return this.attr;
    }
}