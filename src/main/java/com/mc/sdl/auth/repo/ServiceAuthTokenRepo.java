package com.mc.sdl.auth.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mc.sdl.constants.SDLConstants;
import com.mc.sdl.auth.entity.ServiceAuthToken;
import com.mc.sdl.auth.enums.Status;

public interface ServiceAuthTokenRepo extends JpaRepository<ServiceAuthToken, Long> {
    
    @Query(value = "select count(*) from service_auth_token sat where sat.service_code = :serviceCode and sat.status = :status", 
           nativeQuery = true)
    Integer fetchCountByServiceCodeAndStatus(@Param(SDLConstants.SERVICE_CODE) String serviceCode, 
                                           @Param(SDLConstants.STATUS) String status);

    List<ServiceAuthToken> findByServiceCode(String serviceCode);

    List<ServiceAuthToken> findByServiceCodeAndStatus(String serviceCode, Status status);

    List<ServiceAuthToken> findByStatus(Status status);

    ServiceAuthToken findByServiceAuthKey(String serviceAuthKey);

    ServiceAuthToken findByServiceCodeAndServiceAuthKeyAndStatus(String serviceCode, String serviceAuthKey, Status status);

    List<ServiceAuthToken> findByAllowedIps(String allowedIps);

    @Query(value = "select * from service_auth_token sat where sat.expires_at < NOW()", 
           nativeQuery = true)
    List<ServiceAuthToken> findExpiredTokens();

    @Query(value = "select * from service_auth_token sat where sat.allowed_ips LIKE CONCAT('%', :ipAddress, '%')", 
           nativeQuery = true)
    List<ServiceAuthToken> findByIpAddress(@Param("ipAddress") String ipAddress);
}
