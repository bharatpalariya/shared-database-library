package com.mc.sdl.auth.dao;

import java.util.List;

import com.mc.scl.exception.CommonException;
import com.mc.scl.exception.CommonExceptionMessages;
import com.mc.sdl.auth.entity.ServiceAuthToken;
import com.mc.sdl.auth.enums.Status;

public interface ServiceAuthTokenDao {

    default void isEmptyOrNull(Boolean bool) {
        if (Boolean.TRUE.equals(bool))
            throw new CommonException(CommonExceptionMessages.DATA_NOT_FOUND);
    }

    List<ServiceAuthToken> saveAll(List<ServiceAuthToken> serviceAuths);

    Integer countByServiceCodeAndStatus(String serviceCode, Status status);

    List<ServiceAuthToken> findByServiceCode(String serviceCode);

    List<ServiceAuthToken> findByServiceCodeAndStatus(String serviceCode, Status status);

    List<ServiceAuthToken> findByStatus(Status status);

    ServiceAuthToken save(ServiceAuthToken serviceAuth);

    ServiceAuthToken findByServiceAuthKey(String serviceAuthKey);

    ServiceAuthToken findByServiceCodeAndServiceAuthKeyAndStatus(String serviceCode, String serviceAuthKey, Status status);

    List<ServiceAuthToken> findByAllowedIps(String allowedIps);

    List<ServiceAuthToken> findExpiredTokens();
}
