package com.mindware.workflow.core.usecase.contract;

import com.mindware.workflow.core.entity.contract.TemplateContract;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.contract.RepositoryTemplateContract;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateTemplateContract extends UseCaseBase<TemplateContract> implements UseCase<TemplateContract> {

    private RepositoryTemplateContract repository;

    private TemplateContract register;

    private Optional<TemplateContract> result = Optional.empty();

    private CreateTemplateContract(){}

    public static CreateTemplateContract create(RepositoryTemplateContract repository, TemplateContract register){
        CreateTemplateContract useCase = new CreateTemplateContract();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio Plantilla Contratos' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register, "'Plantilla Contratos' no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute() {
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        Optional<TemplateContract> searchByFileName = repository.getByFileName(this.register.getFileName());

        if(isNewTemplateAndFilenameExist(isNew,searchByFileName)){
            throw new UseCaseException(String.format("Plantilla con nombre '%s' ya se encuentran registrados", this.register.getFileName()));
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
    protected Optional<TemplateContract> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<TemplateContract> getByNaturalKey() {
        return repository.getByFileName(this.register.getFileName());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<TemplateContract> getResult() {
        return result;
    }

    private boolean isNewTemplateAndFilenameExist(boolean isNew, Optional<TemplateContract> searchByFileName){
        return isNew && searchByFileName.isPresent();
    }
}
