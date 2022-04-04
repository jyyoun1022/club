package org.zerock.club.secutiry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerock.club.entity.ClubMember;
import org.zerock.club.repository.ClubMemberRepository;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubOAuth2UserDetailsServices extends DefaultOAuth2UserService {

    private final ClubMemberRepository clubMemberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("userRequest : "+userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();

        //OAuth로 연결한 클라이언트 이르모가 이떄 사용한 파라미터들을 출력합니다.
        log.info("clientName : "+clientName);
        log.info(userRequest.getAdditionalParameters());
        //DefaultOAuth2UserRequest라는 타입의 파라미터와 OAuth2User 라는 타입의 리턴 타입을 반환합니다.

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("=======================");

        //loadUser()에서 사용하는 OAuth2UserRequest 는 현재 어떤 서비스를 통해서 로그인이 이루어 졌는지
        //알아내고 전달된 값들을 추출할 수 있는 데이터를 Map<String,Object>형태로 사용할 수 있습니다.

        //sub, picture,email,email_verified 항목이 출력됩니다.
        // sub:101788431424256095365
        //picture:https://lh3.googleusercontent.com/a/default-user=s96-c
        //email:jyyoun1021@gmail.com
        //email_verified:true
        oAuth2User.getAttributes().forEach((k,v)->{
            log.info(k + ":"+v);
        });

        String email = null;

        //이메일 정보 추출
        if(clientName.equals("Google")){
            email = oAuth2User.getAttribute("email");
        }
        log.info(email);




        return oAuth2User;

    }
    //ClubMemberRepository를 이용해서 소셜 로그인한 이메일 을 처리하는 부분
    private ClubMember saveSocialMember(String email){
        Optional<ClubMember> result = clubMemberRepository.findByEmail(email,true);

    }
}
