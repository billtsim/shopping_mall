package com.a88.service.inter;

import com.a88.Pojo.user;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

public interface userService {
    boolean sendPasswordResetEmail(String email);

    user getByEmail(String email);


    void save(user user);

    user login(String usernameOrEmail, String password);

    user getByResetToken(String token);

    boolean resetPassword(String token, String newPassword);


    boolean isTokenValid(LocalDateTime tokenCreationTime);

    List<user> getUserinfo(Integer id, String username, String email);

    int checkEmail(String newEmail);

    boolean sendVerificationEmail(String newEmail, String username);

    ResponseEntity<Void> resetEmail(String token, String newEmail) throws UnsupportedEncodingException;

    boolean updatePassword(String username, String password, String newPassword);
}
