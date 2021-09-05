package com.mindware.workflow.persistence.kiosco;

import com.mindware.workflow.core.entity.kiosco.ProductKiosco;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperProductKiosco {

    void add(ProductKiosco productKiosco);

    void update(ProductKiosco productKiosco);

    List<ProductKiosco> getAll();

    ProductKiosco getProductKioscoById(@Param("id") UUID id);

    ProductKiosco getByProductName(@Param("product") String productName);
}
