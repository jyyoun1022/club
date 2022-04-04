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
import org.zerock.club.entity.ClubMemberRole;
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

        //OAuth로 연결한 클라이언트 이름이 이떄 사용한 파라미터들을 출력합니다.
        log.info("clientName : "+clientName);//"google"
        log.info("파라미터 어떻게 나오니? : "+userRequest.getAdditionalParameters()); //id_token{xxx}으로 출력
        //DefaultOAuth2UserRequest라는 타입의 파라미터와 OAuth2User 라는 타입의 리턴 타입을 반환합니다.

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("OAuth2User : "+oAuth2User);// Name,Granted Authorities,User Attrivutes 출력
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


        ClubMember member = saveSocialMember(email);



        return oAuth2User;

    }
    //ClubMemberRepository를 이용해서 소셜 로그인한 이메일 을 처리하는 부분
    private ClubMember saveSocialMember(String email){
        Optional<ClubMember> result = clubMemberRepository.findByEmail(email,true);

        //추출된 이메일 주소로 현재 데이터베이스에 있는 사용자가 아니라면 자동으로 회원 가입을 처리합니다.
        if(result.isPresent()){
            return result.get();
        }
        ClubMember clubMember = ClubMember.builder().
                email(email)
                .name(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();

        clubMember.addMemberRole(ClubMemberRole.USER);

        clubMemberRepository.save(clubMember);

        return clubMember;

    }
}
