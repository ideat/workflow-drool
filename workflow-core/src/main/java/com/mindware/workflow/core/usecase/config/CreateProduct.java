package com.mindware.workflow.core.usecase.config;

import com.mindware.workflow.core.entity.config.Product;
import com.mindware.workflow.core.service.data.config.RepositoryProduct;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class  CreateProduct extends UseCaseBase<Product> implements UseCase<Product>  {

    private RepositoryProduct repository;

    private Product register;

    private Optional<Product> result = Optional.empty();

    private CreateProduct(){}

    public static CreateProduct create(RepositoryProduct repository, Product register){
        CreateProduct useCase = new CreateProduct();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio producto' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register,"'Registro Producto' es requerido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute(){
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        Optional<Product> searchByName = repository.getProductByName(this.register.getName());

        if(isNew){
            this.register.setId(UUID.randomUUID());
            repository.addProduct(this.register);
            result = Optional.of(this.register);
        }else{
            repository.updateProduct(this.register);
        }
    }

    @Override
    protected Optional<Product> getById() {
        return repository.getProductById(this.register.getId());
    }

    @Override
    protected Optional<Product> getByNaturalKey() {
        return repository.getProductByName(this.register.getName());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<Product> getResult() {
        return result;
    }
}
