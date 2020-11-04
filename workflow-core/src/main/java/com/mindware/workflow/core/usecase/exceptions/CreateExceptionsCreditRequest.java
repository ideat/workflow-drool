package com.mindware.workflow.core.usecase.exceptions;

import com.mindware.workflow.core.entity.exceptions.ExceptionsCreditRequest;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.exceptions.RepositoryExceptionsCreditRequest;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateExceptionsCreditRequest extends UseCaseBase<ExceptionsCreditRequest> implements UseCase<ExceptionsCreditRequest> {
    private RepositoryExceptionsCreditRequest repository;

    private ExceptionsCreditRequest register;

    private Optional<ExceptionsCreditRequest> result = Optional.empty();

    private CreateExceptionsCreditRequest(){}

    public static CreateExceptionsCreditRequest create(RepositoryExceptionsCreditRequest repository, ExceptionsCreditRequest register){
        CreateExceptionsCreditRequest useCase = new CreateExceptionsCreditRequest();
        useCase.repository = Objects.requireNonNull(repository, "Repositorio'RepositoryExceptionsCreditRequest' es requerido");
        useCase.register = Objects.requireNonNull(register,"Registro 'ExceptionsCreditRequest' es requerido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute() {
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        Optional<ExceptionsCreditRequest> searchByCodeExceptionNumberRequest = repository.getByCodeExceptionNumberRequest(this.register.getCodeException(),this.register.getNumberRequest());
        if (ifNewExceptionCreditRequestAndIdExceptionAndNumberRequestExist(isNew,searchByCodeExceptionNumberRequest)){
            throw  new UseCaseException(String.format("Exception %s y Solicitud %s ya fueron registrados",this.register.getCodeException(),this.register.getNumberRequest()));
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
    protected Optional<ExceptionsCreditRequest> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<ExceptionsCreditRequest> getByNaturalKey() {
        return repository.getByCodeExceptionNumberRequest(this.register.getCodeException(),this.register.getNumberRequest());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<ExceptionsCreditRequest> getResult() {
        return result;
    }

    private boolean ifNewExceptionCreditRequestAndIdExceptionAndNumberRequestExist(boolean isNew, Optional<ExceptionsCreditRequest> searchByCodeExceptionNumberRequest){
        return isNew && searchByCodeExceptionNumberRequest.isPresent();
    }
}
