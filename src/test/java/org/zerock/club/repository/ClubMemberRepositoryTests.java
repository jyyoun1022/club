package org.zerock.club.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.club.entity.ClubMember;
import org.zerock.club.entity.ClubMemberRole;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ClubMemberRepositoryTests {
    @Autowired
    private ClubMemberRepository clubMemberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("100더미생성")
    void insertDummies(){
        //1-80까지는 USER만 지정,
        //81-90까지는 USER,MANAGER,
        ///91-100까지는 USER,MANAGER,ADMIN
        IntStream.rangeClosed(1,100).forEach(i->{
            ClubMember clubMember = ClubMember.builder()
                    .email("user"+i+"@naver.com")
                    .name("사용자"+i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();
            //default role
            clubMember.addMemberRole(ClubMemberRole.USER);

            if(i>80){
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            }
            if(i>90){
                clubMember.addMemberRole(ClubMemberRole.ADMIN);
            }
            clubMemberRepository.save(clubMember);

        });
    }
    @Test
    @DisplayName("회원데이터 조회")
    public void testRead(){

        Optional<ClubMember> result = clubMemberRepository.findByEmail("user28@naver.com", false);

        if(result.isPresent()){
            System.out.println(result.get());
        }
    }
}
