package com.sdl.auth.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdl.auth.entity.ServiceAuth;
import com.sdl.auth.enums.Status;
import com.sdl.auth.repo.ServiceAuthRepo;

@Component
public class ServiceAuthDaoImpl implements ServiceAuthDao {

    @Autowired
    ServiceAuthRepo repo;

    @Override
    public List<ServiceAuth> saveAll(List<ServiceAuth> serviceAuths) {
        return repo.saveAll(serviceAuths);
    }

    @Override
    public Integer countByClientCodeAndStatus(String clientCode, Status status) {
        return repo.fetchCountByClientCodeAndStatus(clientCode, status.name());
    }

    @Override
    public List<ServiceAuth> findByClientCode(String clientCode) {
        List<ServiceAuth> result = repo.findByClientCode(clientCode);
        isEmptyOrNull(result.isEmpty());
        return result;
    }

    @Override
    public List<ServiceAuth> findByClientCodeAndStatus(String clientCode, Status status) {
        List<ServiceAuth> result = repo.findByClientCodeAndStatus(clientCode, status);
        isEmptyOrNull(result.isEmpty());
        return result;
    }

    @Override
    public List<ServiceAuth> findByStatus(Status status) {
        List<ServiceAuth> result = repo.findByStatus(status);
        isEmptyOrNull(result.isEmpty());
        return result;
    }

    @Override
    public ServiceAuth save(ServiceAuth serviceAuth) {
        return repo.save(serviceAuth);
    }

    @Override
    public ServiceAuth findByToken(String token) {
        ServiceAuth result = repo.findByToken(token);
        isEmptyOrNull(result == null);
        return result;
    }
}
