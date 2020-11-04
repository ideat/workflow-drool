package com.mindware.workflow.core.usecase.contract;

import com.mindware.workflow.core.entity.contract.Contract;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.contract.RepositoryContract;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateContract extends UseCaseBase<Contract> implements UseCase<Contract> {
    private RepositoryContract repository;

    private Contract register;

    private Optional<Contract> result = Optional.empty();

    private CreateContract(){ }

    public static CreateContract create(RepositoryContract repository, Contract register){
        CreateContract useCase = new CreateContract();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio Contract' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register,"'Registro Contract' no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute() {
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        Optional<Contract> searchByNumberRequest = repository.getByNumberRequest(this.register.getNumberRequest());

        if(isNewContractAndNumberRequestExist(isNew,searchByNumberRequest)){
            throw new UseCaseException(String.format("Numero de solicitud '%s' ya tiene un contrato generado",this.register.getNumberRequest()));
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
    protected Optional<Contract> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<Contract> getByNaturalKey() {
        return repository.getByNumberRequest(this.register.getNumberRequest());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<Contract> getResult() {
        return result;
    }

    private boolean isNewContractAndNumberRequestExist(boolean isNew, Optional<Contract> searchByNumberRequest){
        return isNew && searchByNumberRequest.isPresent();
    }
}
