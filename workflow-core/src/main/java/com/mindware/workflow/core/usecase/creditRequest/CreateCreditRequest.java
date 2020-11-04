package com.mindware.workflow.core.usecase.creditRequest;

import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequest;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateCreditRequest extends UseCaseBase<CreditRequest> implements UseCase<CreditRequest> {

    private RepositoryCreditRequest repository;

    private CreditRequest register;

    private Optional<CreditRequest> result = Optional.empty();

    private CreateCreditRequest(){}

    public static CreateCreditRequest create(RepositoryCreditRequest repository, CreditRequest register){
        CreateCreditRequest useCase = new CreateCreditRequest();
        useCase.repository = Objects.requireNonNull(repository, "'Repositorio solicitud credito' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register,"'Registro' no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute(){
        super.execute();
        boolean isNew = !this.searchById.isPresent();

        Optional<CreditRequest> searchByNumberRequest = repository.getCreditRequestByNumberRequest(this.register.getNumberRequest());

        if(ifNewCreditRequestAndNumberRequestExist(isNew,searchByNumberRequest)
                || existNumberRequestInOtherCreditRequest(this.register.getId(),searchByNumberRequest)){
            throw new UseCaseException(String.format("Numero solicitud '%s' ya se encuentra registrado", this.register.getNumberRequest()));
        }

        if(isNew){
            this.register.setId(UUID.randomUUID());
            this.register.setRequestDate(LocalDate.now());
            repository.addCreditRequest(this.register);
            result = Optional.of(this.register);

        }else{
            repository.updateCreditRequest(this.register);
        }

    }

    @Override
    protected Optional<CreditRequest> getById() {
        return repository.getCreditRequestById(this.register.getId());
    }

    @Override
    protected Optional<CreditRequest> getByNaturalKey() {
        return repository.getCreditRequestByNumberRequest(this.register.getNumberRequest());
    }

    @Override
    protected String getMessageError() {
        return String.format("Numero solicitud '%s' ya se encuentra registrado",this.register.getNumberRequest());
    }

    @Override
    public Optional<CreditRequest> getResult() {
        return result;
    }

    private boolean ifNewCreditRequestAndNumberRequestExist(boolean isNew, Optional<CreditRequest> searchByNumberRequest){
        return isNew && searchByNumberRequest.isPresent();
    }

    private boolean existNumberRequestInOtherCreditRequest(UUID id, Optional<CreditRequest> searchByNumberRequest){
        return searchByNumberRequest.isPresent() && !searchByNumberRequest.get().getId().equals(id);
    }
}
