package com.example.mongoDB.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private  EmailService emailService;

    @Test
    void testSendMail(){
        emailService.sendEmail("darshanpatelm5555@gmail.com","Testing java mail sender","hi app kaise hai?");
    }
}
