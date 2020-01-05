package com.pcz.blog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Test
    public void passwordGeneratorTest() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = "123456";
        System.out.println(passwordEncoder.encode(password));
    }
}
