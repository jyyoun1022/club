package org.zerock.club.secutiry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.club.entity.ClubMember;
import org.zerock.club.repository.ClubMemberRepository;
import org.zerock.club.secutiry.dto.ClubAuthMemberDTO;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {

    private final ClubMemberRepository clubMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("ClubUserDetailsService loadUserByUsername"+username);

        Optional<ClubMember> result=clubMemberRepository.findByEmail(username, false);
        System.out.println("username:"+username);
        if(result.isPresent()==false){
            throw new UsernameNotFoundException("Check Email or Social ");
        }

        ClubMember clubMember=result.get();

        System.out.println(clubMember.getRoleSet().toString());
        log.info("--------------------------");
        log.info(clubMember);

        //ClubMember를 UserDetails 타입으로 처리하기 위해서 ClubAuthMemberDTO 타입으로 변환
        ClubAuthMemberDTO clubAuthMember=new ClubAuthMemberDTO(
                clubMember.getEmail(),
                clubMember.getPassword(),

                //ClubMemberRole은 스프링 시큐리티에서 사용하는 SimpleGrantedAuthority로 변환,
                //이때 'ROLE_'라는 접두어를 추가해서 사용한다.
                //user95@zerock.org 같은 경우 롤이 3개다 [USER, MANAGER, ADMIN]
                //이 각각을 [ROLE_ADMIN, ROLE_MANAGER, ROLE_USER]로 변환해서 Set으로 넣어주고 그 컬렉션을 반환한다.
                clubMember.getRoleSet().stream()
                        .map(role->new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toSet()),
                clubMember.isFromSocial());


        clubAuthMember.setName(clubMember.getName());
        System.out.println(clubAuthMember.getAuthorities().toString());
        return clubAuthMember;
    }

}
