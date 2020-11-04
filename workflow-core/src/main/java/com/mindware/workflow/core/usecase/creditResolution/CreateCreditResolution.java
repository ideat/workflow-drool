package com.mindware.workflow.core.usecase.creditResolution;

import com.mindware.workflow.core.entity.creditResolution.CreditResolution;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.creditResolution.RepositoryCreditResolution;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateCreditResolution extends UseCaseBase<CreditResolution> implements UseCase<CreditResolution> {
    private RepositoryCreditResolution repository;

    private CreditResolution register;

    private Optional<CreditResolution> result = Optional.empty();

    private CreateCreditResolution(){}

    public static CreateCreditResolution create(RepositoryCreditResolution repository, CreditResolution register){
        CreateCreditResolution useCase = new CreateCreditResolution();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio Resolucion' es requerido");
        useCase.register = Objects.requireNonNull(register,"'Registro Resolucion' es requerido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute() {
        super.execute();
        Optional<CreditResolution> searchByNumberRequest = repository.getByNumberRequest(this.register.getNumberRequest());
        boolean isNew = !this.searchById.isPresent();

        if(ifNewCreditResolutionAndNumberRequestExist(isNew,searchByNumberRequest)
                || existNumberRequestInOtherCreditResolution(this.register.getId(),searchByNumberRequest)){
            throw new UseCaseException(String.format("Numero solicitud '%s' ya tiene una resolucion de creditos",
                    this.register.getNumberRequest()));
        }

        if(isNew){
            this.register.setId(UUID.randomUUID());
            repository.add(this.register);
            result = Optional.of(this.register);
        }else {
            repository.update(this.register);
        }
    }

    @Override
    protected Optional<CreditResolution> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<CreditResolution> getByNaturalKey() {
        return Optional.empty();
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<CreditResolution> getResult() {
        return result;
    }

    private boolean ifNewCreditResolutionAndNumberRequestExist(boolean isNew, Optional<CreditResolution> searchByNumberRequest){
        return isNew && searchByNumberRequest.isPresent();
    }

    private boolean existNumberRequestInOtherCreditResolution(UUID id,Optional<CreditResolution> searchByNumberRequest){
        return searchByNumberRequest.isPresent() && !searchByNumberRequest.get().getId().equals(id);
    }
}
