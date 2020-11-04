package com.mindware.workflow.core.usecase.cashFlow;

import com.mindware.workflow.core.entity.cashFlow.CashFlow;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.cashFlow.RepositoryCashFlow;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateCashFlow extends UseCaseBase<CashFlow> implements UseCase<CashFlow> {
    private RepositoryCashFlow repository;

    private CashFlow register;

    private Optional<CashFlow> result = Optional.empty();

    private CreateCashFlow(){}

    public static CreateCashFlow create(RepositoryCashFlow repository, CashFlow register){
        CreateCashFlow useCase = new CreateCashFlow();
        useCase.repository = Objects.requireNonNull(repository, "'Repositorio Flujo de Caja' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register,"'Registro' no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute() {
        super.execute();
        boolean isNew = !this.searchById.isPresent();

        Optional<CashFlow> searchByNumberRequest = repository.getByNumberRequest(this.register.getNumberRequest());

        if(ifNewCashFlowAndNumberRequestExist(isNew,searchByNumberRequest)){
            throw new UseCaseException(String.format("Numero de solicitud '%s' ya se encuentra registrado",this.register.getNumberRequest()));
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
    protected Optional<CashFlow> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<CashFlow> getByNaturalKey() {
        return repository.getByNumberRequest(this.register.getNumberRequest());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<CashFlow> getResult() {
        return result;
    }

    private boolean ifNewCashFlowAndNumberRequestExist(boolean isNew, Optional<CashFlow> searchByNumberRequest){
        return isNew && searchByNumberRequest.isPresent();
    }
}
