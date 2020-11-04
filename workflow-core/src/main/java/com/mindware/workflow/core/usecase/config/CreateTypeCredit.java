package com.mindware.workflow.core.usecase.config;

import com.mindware.workflow.core.entity.config.TypeCredit;
import com.mindware.workflow.core.entity.rol.Option;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.config.RepositoryTypeCredit;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateTypeCredit extends UseCaseBase<TypeCredit> implements UseCase<TypeCredit> {

    private RepositoryTypeCredit repository;

    private TypeCredit register;

    private Optional<TypeCredit> result = Optional.empty();

    private CreateTypeCredit(){}

    public static CreateTypeCredit create(RepositoryTypeCredit repository, TypeCredit register){
        CreateTypeCredit useCase = new CreateTypeCredit();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio TypeCredit' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register,"'Registro TypeCredit' no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute() {
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        Optional<TypeCredit> searchByExternalCode = repository.getByExternalCode(this.register.getExternalCode());

        if(isNewAndExternalCodeExist(isNew,searchByExternalCode)){
            throw new UseCaseException(String.format("Codigo externo '%s' del tipo de credito '%s' ya se encuentra registrado",
                            this.register.getExternalCode(),this.register.getDescription()));
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
    protected Optional<TypeCredit> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<TypeCredit> getByNaturalKey() {
        return repository.getByExternalCode(this.register.getExternalCode());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<TypeCredit> getResult() {
        return result;
    }

    private boolean isNewAndExternalCodeExist(boolean isNew, Optional<TypeCredit> searchByExternalCode){
        return isNew && searchByExternalCode.isPresent();
    }
}
