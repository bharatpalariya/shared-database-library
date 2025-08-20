package com.sdl.auth.dao;

import java.util.List;

import com.sdl.auth.exception.SDLException;
import com.sdl.auth.exception.SDLMessages;
import com.sdl.auth.entity.ServiceAuthToken;
import com.sdl.auth.enums.Status;

public interface ServiceAuthTokenDao {

    default void isEmptyOrNull(Boolean bool) {
        if (Boolean.TRUE.equals(bool))
            throw new SDLException(SDLMessages.DATA_NOT_FOUND_CODE,
                    SDLMessages.DATA_NOT_FOUND_MESSAGE + " For Service Auth");
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
