package com.mindware.workflow.persistence.kiosco;

import com.mindware.workflow.core.entity.kiosco.ProductKiosco;
import com.mindware.workflow.core.service.data.kiosco.RepositoryProductKiosco;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryProductKioscoMybatis implements RepositoryProductKiosco {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperProductKiosco mapper;

    RepositoryProductKioscoMybatis(){}

    public static RepositoryProductKiosco create(SqlSessionFactory sqlSessionFactory){
        RepositoryProductKioscoMybatis repository = new RepositoryProductKioscoMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(ProductKiosco productKiosco) {
        mapper.add(productKiosco);
    }

    @Override
    @Transactional
    public void update(ProductKiosco productKiosco) {
        mapper.update(productKiosco);
    }

    @Override
    @Transactional
    public List<ProductKiosco> getAll() {
        return mapper.getAll();
    }

    @Override
    @Transactional
    public Optional<ProductKiosco> getProductKioscoById(UUID id) {
        return Optional.ofNullable(mapper.getProductKioscoById(id));
    }

    @Override
    @Transactional
    public Optional<ProductKiosco> getByProductName(String productName) {
        return Optional.ofNullable(mapper.getByProductName(productName));
    }
}
