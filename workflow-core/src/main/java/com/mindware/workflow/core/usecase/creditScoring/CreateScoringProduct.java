package com.mindware.workflow.core.usecase.creditScoring;

import com.mindware.workflow.core.entity.creditScoring.ScoringProduct;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.creditScoring.RepositoryScoringProduct;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateScoringProduct extends UseCaseBase<ScoringProduct> implements UseCase<ScoringProduct> {

    private RepositoryScoringProduct repository;

    private ScoringProduct register;

    private Optional<ScoringProduct> result = Optional.empty();

    public static CreateScoringProduct create(RepositoryScoringProduct repository, ScoringProduct register){
        CreateScoringProduct useCase = new CreateScoringProduct();
        useCase.repository = Objects.requireNonNull(repository,"Repositorio Scoring Product es requerido");
        useCase.register = Objects.requireNonNull(register,"Registro Scoring Product es requerido");
        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute(){
        super.execute();
        boolean isNew = !this.searchById.isPresent();

        Optional<ScoringProduct> searchByName = repository.getByName(this.register.getName());

        if(ifNewScoringProductAndNameExist(isNew,searchByName)
                || existNameIntoOtherScoringProduct(this.register.getId(), searchByName)){
            throw new UseCaseException(String.format("Nombre Scoring '%s' ya se encuentra registrado",this.register.getName()));
        }

        if(isNew){
            this.register.setId(UUID.randomUUID());
            repository.add(this.register);
        }else{
            repository.update(this.register);
        }
    }

    @Override
    public Optional<ScoringProduct> getResult() {
        return result;
    }

    @Override
    protected Optional<ScoringProduct> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<ScoringProduct> getByNaturalKey() {
        return repository.getByName(this.register.getName());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    private boolean ifNewScoringProductAndNameExist(boolean isNew, Optional<ScoringProduct> searchByName){
        return isNew && searchByName.isPresent();
    }

    private boolean existNameIntoOtherScoringProduct(UUID id, Optional<ScoringProduct> searchByName){
        return searchByName.isPresent() && !searchByName.get().getId().equals(id);
    }
}
