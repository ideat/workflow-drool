package com.mindware.workflow.core.usecase.creditRequest;

import com.mindware.workflow.core.entity.creditRequest.CreditRequestEnabled;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequestEnabled;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateCreditRequestEnabled extends UseCaseBase<CreditRequestEnabled> implements UseCase<CreditRequestEnabled> {

    private RepositoryCreditRequestEnabled repository;
    
    private CreditRequestEnabled register;
    
    private Optional<CreditRequestEnabled> result = Optional.empty();
    
    private CreateCreditRequestEnabled(){}
    
    public static CreateCreditRequestEnabled create(RepositoryCreditRequestEnabled repository, CreditRequestEnabled register){
        CreateCreditRequestEnabled useCase = new CreateCreditRequestEnabled();
        useCase.repository = Objects.requireNonNull(repository, "'Repositorio habilitacion operacion' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register,"'Registro habilitacion operacion' no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute(){
        super.execute();
        boolean isNew = !this.searchById.isPresent();

        Optional<CreditRequestEnabled> searchByNumberRequest = repository.getByNumberRequestOpen(this.register.getNumberRequest());

        if(ifNewCreditRequestEnabledAndNumberRequestExistAndActive(isNew, searchByNumberRequest)){
            throw new UseCaseException(String.format("Numero solicitud '%s' ya se encuentra habilitada", this.register.getNumberRequest()));
        }

        if(isNew){
            this.register.setId(UUID.randomUUID());
            this.register.setEnabledDateTime(Instant.now());
            repository.add(this.register);
            result = Optional.of(this.register);
        }else{
            repository.update(this.register);
        }
    }

    @Override
    public Optional<CreditRequestEnabled> getResult() {
        return result;
    }

    @Override
    protected Optional<CreditRequestEnabled> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<CreditRequestEnabled> getByNaturalKey() {
        return Optional.empty();
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    private boolean ifNewCreditRequestEnabledAndNumberRequestExistAndActive(boolean isNew, Optional<CreditRequestEnabled> searchByNumberRequest){
        return isNew && searchByNumberRequest.isPresent() ;
    }
}
