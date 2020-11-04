package com.mindware.workflow.persistence.office;

import com.mindware.workflow.core.entity.config.Office;
import com.mindware.workflow.core.service.data.office.RepositoryOffice;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryOfficeMybatis implements RepositoryOffice {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperOffice mapper;

    RepositoryOfficeMybatis(){}

    public static RepositoryOffice create(SqlSessionFactory sqlSessionFactory){
        RepositoryOfficeMybatis repository = new RepositoryOfficeMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void addOffice(Office office) {
        mapper.addOffice(office);
    }

    @Override
    @Transactional
    public void updateOffice(Office office) {
        mapper.updateOffice(office);
    }

    @Override
    @Transactional
    public void updateOfficeSignatorie(Office office) {
        mapper.updateOfficeSignatorie(office);
    }


    @Override
    @Transactional
    public Optional<Office> getOfficeByInternalCode(int code) {
        return Optional.ofNullable(mapper.getOfficeByInternalCode(code));
    }

    @Override
    @Transactional
    public Optional<Office> getOfficeById(UUID id) {
        return Optional.ofNullable(mapper.getOfficeById(id));
    }

    @Override
    @Transactional
    public List<Office> getAllOffices() {
        return mapper.getAllOffices();
    }

    @Override
    public List<Office> getOfficeCity() {
        return mapper.getOfficeCity();
    }
}
