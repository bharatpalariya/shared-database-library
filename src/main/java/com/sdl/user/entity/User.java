package com.sdl.user.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sdl.user.enums.UserStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long userId;

    private Integer operatorId;

    private Integer clientId;

    private String username;

    private String email;

    private String mobileNo;

    @JsonIgnore
    private String password;

    private Integer roleId;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private Integer createdBy;

    private Integer updatedBy;

    private Date createdAt;

    private Date updatedAt;

    public User(String username, String email, String mobileNo, String password, Integer roleId) {
        this.username = username;
        this.email = email;
        this.mobileNo = mobileNo;
        this.password = password;
        this.roleId = roleId;
        this.status = UserStatus.ACTIVE;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public User setUserDetails(String username, String email, String mobileNo, Integer roleId) {
        this.userId = null;
        this.username = username;
        this.email = email;
        this.mobileNo = mobileNo;
        this.roleId = roleId;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        return this;
    }

    public User updateStatus(UserStatus status) {
        this.status = status;
        this.updatedAt = new Date();
        return this;
    }

    public User updatePassword(String password) {
        this.password = password;
        this.updatedAt = new Date();
        return this;
    }

    public User updateProfile(String firstName, String lastName, String mobileNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNo = mobileNo;
        this.updatedAt = new Date();
        return this;
    }
}
