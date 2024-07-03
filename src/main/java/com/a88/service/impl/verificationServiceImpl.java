package com.a88.service.impl;
import com.a88.utils.VerificationCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
@Service
public class verificationServiceImpl {

    @Autowired
    private JavaMailSender mailSender;

    public String sendVerificationCode(String email) {
        String code = VerificationCodeUtil.generateCode();
        VerificationCodeUtil.storeCode(email, code);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your verification code");
        message.setText("Your verification code is: " + code);
        mailSender.send(message);
        return code;
    }
}
