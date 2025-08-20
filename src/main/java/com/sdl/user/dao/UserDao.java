package com.sdl.user.dao;

import java.util.List;

import com.mc.scl.exception.CommonException;
import com.mc.scl.exception.CommonExceptionMessages;
import com.sdl.user.entity.User;
import com.sdl.user.enums.UserStatus;

public interface UserDao {

    default void isEmptyOrNull(Boolean bool) {
        if (Boolean.TRUE.equals(bool))
            throw new CommonException(CommonExceptionMessages.DATA_NOT_FOUND);
    }

    List<User> saveAll(List<User> users);

    Integer countByUsernameAndStatus(String username, UserStatus status);

    List<User> findByUsername(String username);

    List<User> findByUsernameAndStatus(String username, UserStatus status);

    List<User> findByStatus(UserStatus status);

    User save(User user);

    User findByEmail(String email);

    User findByMobileNo(String mobileNo);

    User findByUsernameAndPassword(String username, String password);

    List<User> findByOperatorId(Integer operatorId);

    List<User> findByClientId(Integer clientId);

    List<User> findByRoleId(Integer roleId);

    User findActiveUserByEmail(String email);

    User findActiveUserByUsername(String username);

    List<User> findByRoleIdAndStatus(Integer roleId, UserStatus status);

    List<User> findByOperatorIdAndClientId(Integer operatorId, Integer clientId);

    List<User> findByNameLike(String name);
}
