package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.Caedec;
import com.mindware.workflow.core.service.data.config.RepositoryCaedec;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryCaedecMybatis implements RepositoryCaedec {
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperCaedec mapper;

    RepositoryCaedecMybatis(){}

    public static  RepositoryCaedec create(SqlSessionFactory sqlSessionFactory){
        RepositoryCaedecMybatis repository = new RepositoryCaedecMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void addCaedec(Caedec caedec) {
        mapper.addCaedec(caedec);
    }

    @Override
    @Transactional
    public void updateCaedec(Caedec caedec) {
        mapper.updateCaedec(caedec);
    }

    @Override
    @Transactional
    public List<Caedec> getAllCaedec() {
        return mapper.getAllCaedec();
    }

    @Override
    public Optional<Caedec> getCaedecByCode(String code) {
        return Optional.ofNullable(mapper.getCaedecByCode(code));
    }

    @Override
    public Optional<Caedec> getCaedecById(UUID id) {
        return Optional.ofNullable(mapper.getCaedecById(id));
    }
}
