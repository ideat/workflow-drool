package com.mindware.workflow.core.service.data.kiosco;

import com.mindware.workflow.core.entity.kiosco.ProductKiosco;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryProductKiosco {

    void add(ProductKiosco productKiosco);

    void update(ProductKiosco productKiosco);

    List<ProductKiosco> getAll();

    Optional<ProductKiosco> getProductKioscoById(UUID id);

    Optional<ProductKiosco> getByProductName(String productName);
}
