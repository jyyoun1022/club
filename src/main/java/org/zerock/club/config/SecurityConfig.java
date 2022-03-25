package org.zerock.club.config;


import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //사용자 계정은 user1, 일단 인메모리 스피링 시큐리티를 실험한다.
        //inMemoryAuthentication()는 인 메모리 authentication를 AuthenticationManagerBuilder에 추가하고
        //원하는데로 인 메모리 authentication를 구성하는 것이 가능한 InMemoryUserDetailsManagerConfigurer 타입을 반환한다.
        //withUser()는 생성되는 UserDetailsManager에 user를 추가하는 것을 허용한다
        //이 함수는 다수의 users를 등록하기 위해서 여러 번 호출이 가능하다

        //사용자 계정은 user1
        auth.inMemoryAuthentication().withUser("user1")
        //1111패스워드 인코딩결과
        .password(" $2a$10$4jlyIwxu2IXABqYBqoWE0eJFTwrrm5bJFjL7ZzNROw/2duaXR0qEi")
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/sample/all").permitAll()
                .antMatchers("/sample/member").hasRole("USER");

//        http.formLogin();// 인증/인가에 문제시 로그인 화면
//        http.csrf().disable();//csrf토큰을 발행하지 않는다.
//        http.logout();//로그아웃 처리
    }

}
