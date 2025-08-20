package com.sdl.auth.dao;

import java.util.List;

import com.sdl.auth.exception.SDLException;
import com.sdl.auth.exception.SDLMessages;
import com.sdl.auth.entity.ServiceAuth;
import com.sdl.auth.enums.Status;

public interface ServiceAuthDao {

    default void isEmptyOrNull(Boolean bool) {
        if (Boolean.TRUE.equals(bool))
            throw new SDLException(SDLMessages.DATA_NOT_FOUND_CODE,
                    SDLMessages.DATA_NOT_FOUND_MESSAGE + " For Service Auth");
    }

    List<ServiceAuth> saveAll(List<ServiceAuth> serviceAuths);

    Integer countByServiceCodeAndStatus(String serviceCode, Status status);

    List<ServiceAuth> findByServiceCode(String serviceCode);

    List<ServiceAuth> findByServiceCodeAndStatus(String serviceCode, Status status);

    List<ServiceAuth> findByStatus(Status status);

    ServiceAuth save(ServiceAuth serviceAuth);

    ServiceAuth findByServiceAuthKey(String serviceAuthKey);

    ServiceAuth findByServiceCodeAndServiceAuthKeyAndStatus(String serviceCode, String serviceAuthKey, Status status);

    List<ServiceAuth> findByAllowedIps(String allowedIps);

    List<ServiceAuth> findExpiredTokens();
}
