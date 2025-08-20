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

    Integer countByClientCodeAndStatus(String clientCode, Status status);

    List<ServiceAuth> findByClientCode(String clientCode);

    List<ServiceAuth> findByClientCodeAndStatus(String clientCode, Status status);

    List<ServiceAuth> findByStatus(Status status);

    ServiceAuth save(ServiceAuth serviceAuth);

    ServiceAuth findByToken(String token);
}
