package com.sdl.auth.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sdl.auth.common.model.VariablesConstant;
import com.sdl.auth.entity.ServiceAuth;
import com.sdl.auth.enums.Status;

public interface ServiceAuthRepo extends JpaRepository<ServiceAuth, Long> {
    
    @Query(value = "select count(*) from service_auth_token sat where sat.service_code = :serviceCode and sat.status = :status", 
           nativeQuery = true)
    Integer fetchCountByServiceCodeAndStatus(@Param(VariablesConstant.SERVICE_CODE) String serviceCode, 
                                           @Param(VariablesConstant.STATUS) String status);

    List<ServiceAuth> findByServiceCode(String serviceCode);

    List<ServiceAuth> findByServiceCodeAndStatus(String serviceCode, Status status);

    List<ServiceAuth> findByStatus(Status status);

    ServiceAuth findByServiceAuthKey(String serviceAuthKey);

    ServiceAuth findByServiceCodeAndServiceAuthKeyAndStatus(String serviceCode, String serviceAuthKey, Status status);

    List<ServiceAuth> findByAllowedIps(String allowedIps);

    @Query(value = "select * from service_auth_token sat where sat.expires_at < NOW()", 
           nativeQuery = true)
    List<ServiceAuth> findExpiredTokens();

    @Query(value = "select * from service_auth_token sat where sat.allowed_ips LIKE %:ipAddress%", 
           nativeQuery = true)
    List<ServiceAuth> findByIpAddress(@Param("ipAddress") String ipAddress);
}
