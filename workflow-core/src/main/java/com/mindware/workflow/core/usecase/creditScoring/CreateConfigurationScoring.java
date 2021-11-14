package com.mindware.workflow.core.usecase.creditScoring;

import com.mindware.workflow.core.entity.creditScoring.ConfigurationScoring;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.creditScoring.RepositoryConfigurationScoring;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateConfigurationScoring extends UseCaseBase<ConfigurationScoring> implements UseCase<ConfigurationScoring> {

    private RepositoryConfigurationScoring repository;

    private ConfigurationScoring register;

    private Optional<ConfigurationScoring> result = Optional.empty();

    private CreateConfigurationScoring(){}

    public static CreateConfigurationScoring create(RepositoryConfigurationScoring repository, ConfigurationScoring register){
        CreateConfigurationScoring useCase = new CreateConfigurationScoring();
        useCase.repository = Objects.requireNonNull(repository,"Repositorio Configuracion Scoring es requerido");
        useCase.register = Objects.requireNonNull(register,"Registro Configuracion Scoring es requerido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute(){
        super.execute();
        boolean isNew = !this.searchById.isPresent();

        Optional<ConfigurationScoring> searchByProduct = repository.getByCategory(this.register.getCategory());

        if(ifNewConfigurationScoringAndProductExist(isNew,searchByProduct)
             || existCategoryIntoOtherConfigurationScoring(this.register.getId(),searchByProduct)){
            throw new UseCaseException(String.format("Nombre categoria '%s' ya se encuentra registrado",this.register.getCategory()));
        }

        if(isNew){
            this.register.setId(UUID.randomUUID());
            repository.add(this.register);
        }else{
            repository.update(this.register);
        }
    }

    @Override
    public Optional<ConfigurationScoring> getResult() {
        return result;
    }

    @Override
    protected Optional<ConfigurationScoring> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<ConfigurationScoring> getByNaturalKey() {
        return repository.getByCategory(this.register.getCategory());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    private boolean ifNewConfigurationScoringAndProductExist(boolean isNew, Optional<ConfigurationScoring> searchByProduct){
        return isNew && searchByProduct.isPresent();
    }

    private boolean existCategoryIntoOtherConfigurationScoring(UUID id, Optional<ConfigurationScoring> searchByProduct){
        return searchByProduct.isPresent() && !searchByProduct.get().getId().equals(id);
    }
}
