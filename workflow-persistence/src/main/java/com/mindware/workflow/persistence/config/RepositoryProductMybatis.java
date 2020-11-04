package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.Product;
import com.mindware.workflow.core.service.data.config.RepositoryProduct;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryProductMybatis implements RepositoryProduct {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperProduct mapper;

    public static RepositoryProduct create(SqlSessionFactory sqlSessionFactory){
        RepositoryProductMybatis repository = new RepositoryProductMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void addProduct(Product product) {
        mapper.addProduct(product);
    }

    @Override
    public void updateProduct(Product product) {
        mapper.updateProduct(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return mapper.getAllProducts();
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(mapper.getProductById(id));
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        return Optional.ofNullable(mapper.getProductByName(name));
    }
}
