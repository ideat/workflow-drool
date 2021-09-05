package com.mindware.workflow.core.usecase.kiosco;

import com.mindware.workflow.core.entity.kiosco.ProductKiosco;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.kiosco.RepositoryProductKiosco;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateProductKiosco extends UseCaseBase<ProductKiosco> implements UseCase<ProductKiosco> {

    private RepositoryProductKiosco repository;

    private ProductKiosco register;

    private Optional<ProductKiosco> result = Optional.empty();

    private CreateProductKiosco(){}

    public static CreateProductKiosco create(RepositoryProductKiosco repository, ProductKiosco register){
        CreateProductKiosco useCase = new CreateProductKiosco();
        useCase.repository = Objects.requireNonNull(repository, "'Repositorio Producto' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register,"'Registro' no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute(){
        super.execute();
        boolean isNew = !this.searchById.isPresent();

        Optional<ProductKiosco> searchByProductName = repository.getByProductName(this.register.getProduct());

        if(ifNewProductAndProductNameExist(isNew,searchByProductName) ||
          existProductNameInOtherProduct(this.register.getId(),searchByProductName)) {
            throw new UseCaseException(String.format("Nombre de producto '%s' ya se encuentra registrdo", this.register.getProduct()));
        }

        if(isNew){
            this.register.setId(UUID.randomUUID());
            repository.add(this.register);
            result = Optional.of(this.register);
        }else{
            repository.update(this.register);
        }
    }


    @Override
    protected Optional<ProductKiosco> getById(){
        return repository.getProductKioscoById(this.register.getId());
    }

    @Override
    protected Optional<ProductKiosco> getByNaturalKey(){
        return repository.getByProductName(this.register.getProduct());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<ProductKiosco> getResult() {
        return result;
    }

    private boolean ifNewProductAndProductNameExist(boolean isNew, Optional<ProductKiosco> searchByProductName){
        return isNew && searchByProductName.isPresent();
    }

    private boolean existProductNameInOtherProduct(UUID id, Optional<ProductKiosco> searchByProductName){
        return searchByProductName.isPresent() && !searchByProductName.get().getId().equals(id);
    }
}
