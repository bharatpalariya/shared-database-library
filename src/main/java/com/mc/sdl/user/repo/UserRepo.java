package com.mc.sdl.user.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mc.sdl.constants.SDLConstants;
import com.mc.sdl.user.entity.User;
import com.mc.sdl.user.enums.UserStatus;

public interface UserRepo extends JpaRepository<User, Long> {
    
    @Query(value = "select count(*) from users u where u.username = :username and u.status = :status", 
           nativeQuery = true)
    Integer fetchCountByUsernameAndStatus(@Param(SDLConstants.USERNAME) String username, 
                                        @Param(SDLConstants.STATUS) String status);

    List<User> findByUsername(String username);

    List<User> findByUsernameAndStatus(String username, UserStatus status);

    List<User> findByStatus(UserStatus status);

    User findByEmail(String email);

    User findByMobileNo(String mobileNo);

    User findByUsernameAndPassword(String username, String password);

    List<User> findByOperatorId(Integer operatorId);

    List<User> findByClientId(Integer clientId);

    List<User> findByRoleId(Integer roleId);

    @Query(value = "select * from users u where u.email = :email and u.status = 'ACTIVE'", 
           nativeQuery = true)
    User findActiveUserByEmail(@Param("email") String email);

    @Query(value = "select * from users u where u.username = :username and u.status = 'ACTIVE'", 
           nativeQuery = true)
    User findActiveUserByUsername(@Param("username") String username);

    @Query(value = "select * from users u where u.role_id = :roleId and u.status = :status", 
           nativeQuery = true)
    List<User> findByRoleIdAndStatus(@Param("roleId") Integer roleId, @Param("status") String status);

    @Query(value = "select * from users u where u.operator_id = :operatorId and u.client_id = :clientId", 
           nativeQuery = true)
    List<User> findByOperatorIdAndClientId(@Param("operatorId") Integer operatorId, 
                                         @Param("clientId") Integer clientId);

    @Query(value = "select * from users u where u.firstname LIKE CONCAT('%', :name, '%') OR u.lastname LIKE CONCAT('%', :name, '%')", 
           nativeQuery = true)
    List<User> findByNameLike(@Param("name") String name);
}
