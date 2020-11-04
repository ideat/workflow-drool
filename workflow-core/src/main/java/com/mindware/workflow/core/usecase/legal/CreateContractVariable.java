package com.mindware.workflow.core.usecase.legal;

import com.mindware.workflow.core.entity.contract.ContractVariable;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.legal.RepositoryContractVariable;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateContractVariable extends UseCaseBase<ContractVariable> implements UseCase<ContractVariable> {

    private RepositoryContractVariable repository;

    private ContractVariable register;

    private Optional<ContractVariable> result = Optional.empty();

    private CreateContractVariable(){}

    public static CreateContractVariable create(RepositoryContractVariable repository, ContractVariable register){
        CreateContractVariable useCase = new CreateContractVariable();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio ContractVariable' es requerido");
        useCase.register = Objects.requireNonNull(register,"'Registro ContractVariable' es requerido");

        useCase.validateBean(register);

        return useCase;
    }

    @Override
    public void execute() {
        super.execute();
        Optional<ContractVariable> searchByName = repository.getByName(this.register.getName());
        boolean isNew = !this.searchById.isPresent();

        if(ifNewContractVariableAndNameExist(isNew,searchByName)){
            throw new UseCaseException(String.format("Nombe de variable '%s' ya esta registrado",this.register.getName()));
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
    protected Optional<ContractVariable> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<ContractVariable> getByNaturalKey() {
        return repository.getByName(this.register.getName());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<ContractVariable> getResult() {
        return result;
    }

    private boolean ifNewContractVariableAndNameExist(boolean isNew, Optional<ContractVariable> searchByName){
        return isNew && searchByName.isPresent();
    }
}
