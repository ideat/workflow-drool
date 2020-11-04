package com.mindware.workflow.persistence.email;

import com.mindware.workflow.core.entity.email.Mail;
import com.mindware.workflow.core.service.data.email.RepositoryMail;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryMailMybatis implements RepositoryMail {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    MapperMail mapper;

    RepositoryMailMybatis(){}

    public static RepositoryMail create(SqlSessionFactory sqlSessionFactory){
        RepositoryMailMybatis repository = new RepositoryMailMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(Mail mail) {
        mapper.add(mail);
    }

    @Override
    @Transactional
    public void update(Mail mail) {
        mapper.update(mail);
    }

    @Override
    @Transactional
    public List<Mail> getByNumberRequest(Integer numberRequest) {
        return mapper.getByNumberRequest(numberRequest);
    }

    @Override
    @Transactional
    public Optional<Mail> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }
}
