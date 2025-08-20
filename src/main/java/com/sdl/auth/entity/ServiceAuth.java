package com.sdl.auth.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sdl.auth.enums.Status;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "service_auth")
@Entity
@Data
@NoArgsConstructor
public class ServiceAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String clientCode;

    private String token;

    private String clientIp;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Long createdBy;

    private Date createdAt;

    private Long updatedBy;

    private Date updatedAt;

    public ServiceAuth(String clientCode, String token, String clientIp, Long loggedInUserId) {
        this.clientCode = clientCode;
        this.token = token;
        this.clientIp = clientIp;
        this.status = Status.ACTIVE;
        this.createdBy = loggedInUserId;
        this.updatedBy = loggedInUserId;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public ServiceAuth setClientCodeAndToken(String clientCode, String token, Long loggedIn) {
        this.id = null;
        this.clientCode = clientCode;
        this.token = token;
        this.createdBy = loggedIn;
        this.updatedBy = loggedIn;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        return this;
    }

    public ServiceAuth updateStatus(Status status, Long userId) {
        this.status = status;
        this.updatedAt = new Date();
        this.updatedBy = userId;
        return this;
    }
}