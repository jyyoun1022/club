package org.zerock.club.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.club.secutiry.dto.ClubAuthMemberDTO;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class SampleController {

    //로그인하지 않은 사용자도 접근할 수 있는
    @GetMapping("/all")
    @PreAuthorize("permitAll()")
    public void exAll(){
        log.info("exAll");
    }

    //로그인한 사용자만이 접근할 수 있는
    //컨트롤러에서 로그인된 사용자 정보를 확인하는 방법은 크게 2가지이다.
    //SecurityContextHolder라는 객체를 사용하는 방법과 직접 파라미터와 어노테이션을 사용하는 방식이 있는데
    //예제에서는 @AuthenticationPrincipla 어노테이션을 사용해서 처리한다.
    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO){
        log.info("exMember");
        log.info("============");
        log.info(clubAuthMemberDTO);
    }

    //관리자(admin)권한이 있는 사용자만이 접근할 수 있는
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public void exAdmin(){
        log.info("exAdmin");
    }

    @PreAuthorize("#clubAuthMember != null && #clubAuthMember.username eq \"user95@naver.com\"")
    @GetMapping("/exOnly")
    public String exMemberOnly(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){
        log.info(clubAuthMember);

        return "/sample/admin";
    }
}
