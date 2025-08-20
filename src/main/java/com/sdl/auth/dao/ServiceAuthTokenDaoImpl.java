package com.sdl.auth.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdl.auth.entity.ServiceAuthToken;
import com.sdl.auth.enums.Status;
import com.sdl.auth.repo.ServiceAuthRepo;

@Component
public class ServiceAuthTokenDaoImpl implements ServiceAuthTokenDao {

    @Autowired
    ServiceAuthRepo repo;

    @Override
    public List<ServiceAuthToken> saveAll(List<ServiceAuthToken> serviceAuths) {
        return repo.saveAll(serviceAuths);
    }

    @Override
    public Integer countByServiceCodeAndStatus(String serviceCode, Status status) {
        return repo.fetchCountByServiceCodeAndStatus(serviceCode, status.name());
    }

    @Override
    public List<ServiceAuthToken> findByServiceCode(String serviceCode) {
        List<ServiceAuthToken> result = repo.findByServiceCode(serviceCode);
        isEmptyOrNull(result.isEmpty());
        return result;
    }

    @Override
    public List<ServiceAuthToken> findByServiceCodeAndStatus(String serviceCode, Status status) {
        List<ServiceAuthToken> result = repo.findByServiceCodeAndStatus(serviceCode, status);
        isEmptyOrNull(result.isEmpty());
        return result;
    }

    @Override
    public List<ServiceAuthToken> findByStatus(Status status) {
        List<ServiceAuthToken> result = repo.findByStatus(status);
        isEmptyOrNull(result.isEmpty());
        return result;
    }

    @Override
    public ServiceAuthToken save(ServiceAuthToken serviceAuth) {
        return repo.save(serviceAuth);
    }

    @Override
    public ServiceAuthToken findByServiceAuthKey(String serviceAuthKey) {
        ServiceAuthToken result = repo.findByServiceAuthKey(serviceAuthKey);
        isEmptyOrNull(result == null);
        return result;
    }

    @Override
    public ServiceAuthToken findByServiceCodeAndServiceAuthKeyAndStatus(String serviceCode, String serviceAuthKey, Status status) {
        ServiceAuthToken result = repo.findByServiceCodeAndServiceAuthKeyAndStatus(serviceCode, serviceAuthKey, status);
        isEmptyOrNull(result == null);
        return result;
    }

    @Override
    public List<ServiceAuthToken> findByAllowedIps(String allowedIps) {
        List<ServiceAuthToken> result = repo.findByAllowedIps(allowedIps);
        isEmptyOrNull(result.isEmpty());
        return result;
    }

    @Override
    public List<ServiceAuthToken> findExpiredTokens() {
        List<ServiceAuthToken> result = repo.findExpiredTokens();
        isEmptyOrNull(result.isEmpty());
        return result;
    }
}
