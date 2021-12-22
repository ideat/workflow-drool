package com.mindware.workflow.core.usecase.creditScoring;

import com.mindware.workflow.core.entity.creditScoring.ScoringCreditRequest;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.creditScoring.RepositoryScoringCreditRequest;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateScoringCreditRequest extends UseCaseBase<ScoringCreditRequest> implements UseCase<ScoringCreditRequest> {
    
    private RepositoryScoringCreditRequest repository;
    
    private ScoringCreditRequest register;
    
    private Optional<ScoringCreditRequest> result = Optional.empty();
    
    private CreateScoringCreditRequest(){}
    
    public static CreateScoringCreditRequest create(RepositoryScoringCreditRequest repository, ScoringCreditRequest register){
        CreateScoringCreditRequest useCase = new CreateScoringCreditRequest();
        useCase.repository = Objects.requireNonNull(repository, "Repositorio Scoring CreditRequest es requerido");
        useCase.register = Objects.requireNonNull(register,"Registro Scoring CreditRequest es requerido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute(){
        super.execute();
        boolean isNew = !this.searchById.isPresent();

        Optional<ScoringCreditRequest> searchByNumberRequest = repository.getByNumberRequest(this.register.getNumberRequest());

        if(ifNewScoringCreditRequestAndNumberRequestExist(isNew,searchByNumberRequest)
            || existNumberRequestIntoOtherScoringCreditRequest(this.register.getId(),searchByNumberRequest)){
            throw new UseCaseException(String.format("Numero de solicitud '%s' ya se encuentra registrado", this.register.getNumberRequest()));
        }

        if(isNew){
            this.register.setId(UUID.randomUUID());
            repository.add(this.register);
        }else{
            repository.update(this.register);
        }
    }
    
    @Override
    public Optional<ScoringCreditRequest> getResult() {
        return Optional.empty();
    }

    @Override
    protected Optional<ScoringCreditRequest> getById() {
        return Optional.empty();
    }

    @Override
    protected Optional<ScoringCreditRequest> getByNaturalKey() {
        return Optional.empty();
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    private boolean ifNewScoringCreditRequestAndNumberRequestExist(boolean isNew, Optional<ScoringCreditRequest> searchByNumberRequest){
        return isNew && searchByNumberRequest.isPresent();
    }

    private boolean existNumberRequestIntoOtherScoringCreditRequest(UUID id, Optional<ScoringCreditRequest> searchByNumberRequest){
        return searchByNumberRequest.isPresent() && !searchByNumberRequest.get().getId().equals(id);
    }
}
