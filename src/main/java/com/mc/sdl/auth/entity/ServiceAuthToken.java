package com.mc.sdl.auth.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mc.sdl.auth.enums.Status;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "service_auth_token")
@Entity
@Data
@NoArgsConstructor
public class ServiceAuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String serviceCode;

    private String serviceAuthKey;

    private String allowedIps;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Date createdAt;

    private Date updatedAt;

    private Date expiresAt;

    public ServiceAuthToken(String serviceCode, String serviceAuthKey, String allowedIps) {
        this.serviceCode = serviceCode;
        this.serviceAuthKey = serviceAuthKey;
        this.allowedIps = allowedIps;
        this.status = Status.ACTIVE;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        // Set expiry to 1 year from now by default
        this.expiresAt = new Date(System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000));
    }

    public ServiceAuthToken setServiceCodeAndKey(String serviceCode, String serviceAuthKey, String allowedIps) {
        this.id = null;
        this.serviceCode = serviceCode;
        this.serviceAuthKey = serviceAuthKey;
        this.allowedIps = allowedIps;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.expiresAt = new Date(System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000));
        return this;
    }

    public ServiceAuthToken updateStatus(Status status) {
        this.status = status;
        this.updatedAt = new Date();
        return this;
    }

    public ServiceAuthToken updateExpiry(Date expiresAt) {
        this.expiresAt = expiresAt;
        this.updatedAt = new Date();
        return this;
    }
}