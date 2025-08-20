package com.sdl.auth.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sdl.auth.common.model.VariablesConstant;
import com.sdl.auth.entity.ServiceAuth;
import com.sdl.auth.enums.Status;

public interface ServiceAuthRepo extends JpaRepository<ServiceAuth, Long> {
    
    @Query(value = "select count(*) from service_auth sa where sa.client_code = :clientCode and sa.status = :status", 
           nativeQuery = true)
    Integer fetchCountByClientCodeAndStatus(@Param(VariablesConstant.CLIENT_CODE) String clientCode, 
                                          @Param(VariablesConstant.STATUS) String status);

    List<ServiceAuth> findByClientCode(String clientCode);

    List<ServiceAuth> findByClientCodeAndStatus(String clientCode, Status status);

    List<ServiceAuth> findByStatus(Status status);

    ServiceAuth findByToken(String token);
}
