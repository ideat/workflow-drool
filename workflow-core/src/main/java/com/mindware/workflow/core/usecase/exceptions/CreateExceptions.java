package com.mindware.workflow.core.usecase.exceptions;

import com.mindware.workflow.core.entity.exceptions.Exceptions;
import com.mindware.workflow.core.entity.rol.Option;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.exceptions.RepositoryExceptions;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateExceptions  extends UseCaseBase<Exceptions> implements UseCase<Exceptions> {
    private RepositoryExceptions repository;

    private Exceptions register;

    private Optional<Exceptions> result = Optional.empty();

    private CreateExceptions(){}

    public static CreateExceptions create(RepositoryExceptions repository, Exceptions register){
        CreateExceptions useCase = new CreateExceptions();
        useCase.repository = Objects.requireNonNull(repository,"Repositorio 'Exceptions' es requerido");
        useCase.register = Objects.requireNonNull(register, "Registro 'Exceptions' es requerido");

        useCase.validateBean(register);

        return useCase;
    }

    @Override
    public void execute() {
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        Optional<Exceptions> searchByInternalCode = repository.getByInternalCode(this.register.getInternalCode());

        if(ifNewExceptionAndInternalCodeExist(isNew,searchByInternalCode)){
            throw new UseCaseException(String.format("Codigo Interno de la Excepcion '%s' ya fue registrado",
                    this.register.getInternalCode()));
        }

        if(isNew) {
            this.register.setId(UUID.randomUUID());
            repository.add(this.register);
            result = Optional.of(this.register);
        }else{
            repository.update(this.register);
        }
    }

    @Override
    protected Optional<Exceptions> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<Exceptions> getByNaturalKey() {
        return repository.getByInternalCode(this.register.getInternalCode());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<Exceptions> getResult() {
        return result;
    }

    private boolean ifNewExceptionAndInternalCodeExist(boolean isNew, Optional<Exceptions> searchByInternalCode){
        return isNew && searchByInternalCode.isPresent();
    }
}
