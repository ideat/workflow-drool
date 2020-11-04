package com.mindware.workflow.core.service.data.config;

import com.mindware.workflow.core.entity.config.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryProduct {
    void addProduct(Product product);

    void updateProduct(Product product);

    List<Product> getAllProducts();

    Optional<Product> getProductById(UUID id);

    Optional<Product> getProductByName(String name);

}
