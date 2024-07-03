package com.a88.service.impl;

import com.a88.Pojo.user;
import com.a88.mapper.userMapper;
import com.a88.service.inter.userService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.a88.utils.VerificationCodeUtil;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class userServiceImpl implements userService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private userMapper UM;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private PasswordEncoder passwordEncoder; // 注入密码编码器

    private static final long TOKEN_EXPIRATION_MINUTES = 30; // 令牌有效期30分钟

    @Override
    public boolean sendPasswordResetEmail(String email) {
        user user = UM.getByEmail(email);
        if (user != null) {
            // 生成重置链接
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setTokenCreationTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            UM.update(user);

            // 构建邮件内容
            Context context = new Context();
            context.setVariable("username", user.getUsername());
            context.setVariable("resetLink", "http://192.168.1.83:5173/reset-password?token=" + token);
            String emailContent = templateEngine.process("reset-password-email", context);

            // 发送邮件
            try {
                sendEmail(email, "重置密码", emailContent);
                return true;
            } catch (MessagingException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }

    }

    private void sendEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public user getByEmail(String email) {
        return UM.getByEmail(email);
    }

    // new user insert operation
    @Override
    public void save(user user) {

        String username = user.getUsername();
        String email = user.getEmail();
        String password = passwordEncoder.encode(user.getPassword());
        String code = user.getCode();

        if (VerificationCodeUtil.validateCode(email, code)) {
            user User = new user();
            User.setUsername(username);
            User.setEmail(email);
            User.setPassword(password); // 这里应该对密码进行加密
            User.setRole("USER");
            User.setCreateTime(LocalDateTime.now());
            User.setUpdateTime(LocalDateTime.now());
            UM.insert(User);
            VerificationCodeUtil.removeCode(email);
        } else {
            throw new IllegalArgumentException("Invalid verification code");
        }


    }

    @Override
    public user login(String usernameOrEmail, String password) {
        user User = UM.getByUsernameOrEmail(usernameOrEmail);
        if (User != null && passwordEncoder.matches(password, User.getPassword())) {
            return User;
        } else {
            return null;
        }
    }

    @Override
    public user getByResetToken(String token) {
        return UM.getByResetToken(token);
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        user user = UM.getByResetToken(token);
        if (user != null && isTokenValid(user.getTokenCreationTime())) {
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            user.setResetToken(null); // 清除重置令牌
            user.setTokenCreationTime(null); // 清除令牌创建时间
            user.setUpdateTime(LocalDateTime.now());
            UM.update(user);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean isTokenValid(LocalDateTime tokenCreationTime) {
        LocalDateTime now = LocalDateTime.now();
        long minutesElapsed = ChronoUnit.MINUTES.between(tokenCreationTime, now);
        return minutesElapsed <= TOKEN_EXPIRATION_MINUTES;
    }

    @Override
    public List<user> getUserinfo(Integer id, String username, String email) {
        return UM.getUserInfo(id, username, email);
    }

    @Override
    public int checkEmail(String newEmail) {
        return UM.checkEmail(newEmail);
    }

    @Override
    public boolean sendVerificationEmail(String newEmail, String username) {
        // 获取用户信息
        user user = UM.getByUsernameOrEmail(username);
        if (user != null) {
            /// 生成验证链接
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setTokenCreationTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            UM.update(user);

            // 构建邮件内容
            Context context = new Context();
            context.setVariable("username", username);
            context.setVariable("verificationLink", "http://192.168.1.83:8080/verify-email-change?token=" + token + "&newEmail=" + newEmail);
            String emailContent = templateEngine.process("verification-email", context);

            try {
                sendEmail(newEmail, "验证您的新电子邮件地址", emailContent);
                return true;
            } catch (MessagingException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }

    }

    @Override
    @Transactional
    public ResponseEntity<Void> resetEmail(String token, String newEmail) throws UnsupportedEncodingException {
        // get user data
        user user = UM.getByResetToken(token);
        if (user != null && user.getResetToken().equals(token)) {
            LocalDateTime tokenCreationTime = user.getTokenCreationTime();
            if (isTokenValid(tokenCreationTime)) {
                // 更新电子邮件地址
                user.setEmail(newEmail);
                user.setResetToken(null);
                user.setTokenCreationTime(null);
                user.setUpdateTime(LocalDateTime.now());
                UM.update(user);


                // 重定向到用户资料页面
                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create("http://192.168.1.83:5173/user-profile"))
                        .build();
            } else  {
                String errorMessage = "您的电子邮件的驗證連結已過期。请重试或联系支持。";
                String encodedMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create("http://192.168.1.83:5173/exception?errorMessage=" + encodedMessage))
                        .build();
            }
        } else {
            String errorMessage = "无法验证您的电子邮件地址。请重试或联系支持。";
            String encodedMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("http://192.168.1.83:5173/exception?errorMessage=" + encodedMessage))
                    .build();
        }
    }

    @Override
    public boolean updatePassword(String username, String password, String newPassword) {
        user user = UM.getByUsernameOrEmail(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            String encodPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodPassword);
            user.setUpdateTime(LocalDateTime.now());
            UM.update(user);
            return true;
        } else {
            return false;
        }
    }
}
