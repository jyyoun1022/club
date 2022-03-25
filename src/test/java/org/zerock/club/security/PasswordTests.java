package org.zerock.club.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncode(){

        String password ="1111";
        String enPW = passwordEncoder.encode(password);
        System.out.println("enPW : "+enPW);
        //$2a$10$McmGcjvD1bONKZI04loRru66yBg9Br/s7vZm.WO8UCY.JLK9XezOm
        boolean matchResult = passwordEncoder.matches(password, enPW);
        System.out.println("matchResult: "+matchResult);
    }
}
