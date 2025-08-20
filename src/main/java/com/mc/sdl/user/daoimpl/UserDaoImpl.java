package com.mc.sdl.user.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mc.sdl.user.dao.UserDao;
import com.mc.sdl.user.entity.User;
import com.mc.sdl.user.enums.UserStatus;
import com.mc.sdl.user.repo.UserRepo;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    UserRepo repo;

    @Override
    public List<User> saveAll(List<User> users) {
        return repo.saveAll(users);
    }

    @Override
    public Integer countByUsernameAndStatus(String username, UserStatus status) {
        return repo.fetchCountByUsernameAndStatus(username, status.name());
    }

    @Override
    public List<User> findByUsername(String username) {
        List<User> result = repo.findByUsername(username);
        isEmptyOrNull(result.isEmpty());
        return result;
    }

    @Override
    public List<User> findByUsernameAndStatus(String username, UserStatus status) {
        List<User> result = repo.findByUsernameAndStatus(username, status);
        isEmptyOrNull(result.isEmpty());
        return result;
    }

    @Override
    public List<User> findByStatus(UserStatus status) {
        List<User> result = repo.findByStatus(status);
        isEmptyOrNull(result.isEmpty());
        return result;
    }

    @Override
    public User save(User user) {
        return repo.save(user);
    }

    @Override
    public User findByEmail(String email) {
        User result = repo.findByEmail(email);
        isEmptyOrNull(result == null);
        return result;
    }

    @Override
    public User findByMobileNo(String mobileNo) {
        User result = repo.findByMobileNo(mobileNo);
        isEmptyOrNull(result == null);
        return result;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User result = repo.findByUsernameAndPassword(username, password);
        isEmptyOrNull(result == null);
        return result;
    }

    @Override
    public List<User> findByOperatorId(Integer operatorId) {
        List<User> result = repo.findByOperatorId(operatorId);
        isEmptyOrNull(result.isEmpty());
        return result;
    }

    @Override
    public List<User> findByClientId(Integer clientId) {
        List<User> result = repo.findByClientId(clientId);
        isEmptyOrNull(result.isEmpty());
        return result;
    }

    @Override
    public List<User> findByRoleId(Integer roleId) {
        List<User> result = repo.findByRoleId(roleId);
        isEmptyOrNull(result.isEmpty());
        return result;
    }

    @Override
    public User findActiveUserByEmail(String email) {
        User result = repo.findActiveUserByEmail(email);
        isEmptyOrNull(result == null);
        return result;
    }

    @Override
    public User findActiveUserByUsername(String username) {
        User result = repo.findActiveUserByUsername(username);
        isEmptyOrNull(result == null);
        return result;
    }

    @Override
    public List<User> findByRoleIdAndStatus(Integer roleId, UserStatus status) {
        List<User> result = repo.findByRoleIdAndStatus(roleId, status.name());
        isEmptyOrNull(result.isEmpty());
        return result;
    }

    @Override
    public List<User> findByOperatorIdAndClientId(Integer operatorId, Integer clientId) {
        List<User> result = repo.findByOperatorIdAndClientId(operatorId, clientId);
        isEmptyOrNull(result.isEmpty());
        return result;
    }

    @Override
    public List<User> findByNameLike(String name) {
        List<User> result = repo.findByNameLike(name);
        isEmptyOrNull(result.isEmpty());
        return result;
    }
}
