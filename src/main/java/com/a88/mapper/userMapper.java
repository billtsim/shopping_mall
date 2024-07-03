package com.a88.mapper;

import com.a88.Pojo.user;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface userMapper {

    void insert(user user);

    user getByUsernameOrEmail(String usernameOrEmail);

    user getByEmail(String email);

    user getByResetToken(String resetToken);
    void update(user user);

    List<user> getUserInfo(Integer id, String username, String email);

    @Select("Select COUNT(*) from user where email= #{newEmail}" )
    int checkEmail(String newEmail);
}
