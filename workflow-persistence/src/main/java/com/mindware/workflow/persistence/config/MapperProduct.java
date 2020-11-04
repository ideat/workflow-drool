package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperProduct {
    void addProduct(Product product);

    void updateProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(UUID id);

    Product getProductByName(String name);

}
