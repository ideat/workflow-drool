package com.mindware.workflow.persistence.comercial.client;

import com.mindware.workflow.core.entity.comercial.client.Client;
import com.mindware.workflow.core.service.data.comercial.client.RepositoryClient;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryClientMybatis implements RepositoryClient {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperClient mapper;

    RepositoryClientMybatis(){}

    public static RepositoryClient create(SqlSessionFactory sqlSessionFactory){
        RepositoryClientMybatis repository = new RepositoryClientMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(Client client) {
        mapper.add(client);
    }

    @Override
    @Transactional
    public void update(Client client) {
        mapper.update(client);
    }

    @Override
    @Transactional
    public Optional<Client> getByIdCardComplete(String idCard,String extension) {
        return Optional.ofNullable(mapper.getByIdCardComplete(idCard,extension));
    }

    @Override
    public Optional<Client> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }

    @Override
    public List<Client> getByUser(String loginUser) {
        return mapper.getByUser(loginUser);
    }

    @Override
    public List<Client> getAll() {
        return mapper.getAll();
    }
}
