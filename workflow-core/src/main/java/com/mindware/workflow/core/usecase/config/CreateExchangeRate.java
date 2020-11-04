package com.mindware.workflow.core.usecase.config;

import com.mindware.workflow.core.entity.config.ExchangeRate;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.config.RepositoryExchangeRate;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateExchangeRate extends UseCaseBase<ExchangeRate> implements UseCase<ExchangeRate> {

    private RepositoryExchangeRate repository;

    private ExchangeRate register;

    private Optional<ExchangeRate> result = Optional.empty();

    private CreateExchangeRate(){}

    public static CreateExchangeRate create(RepositoryExchangeRate repository, ExchangeRate register){
        CreateExchangeRate useCase = new CreateExchangeRate();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio ExchangeRate' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register,"'Registro' ExchangeRate no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute() {
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        Optional<ExchangeRate> searchByValidityDate = repository.getByValidityDate(this.register.getStartValidity()
                ,this.register.getEndValidity());

        if(isNewStartValidityAndEndValidityExist(isNew,searchByValidityDate)){
            throw new UseCaseException(String.format("Tipo cambio registrado entre fechas '%s' y '%s' ya existe"
                    ,this.register.getStartValidity(),this.register.getEndValidity()));
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
    protected Optional<ExchangeRate> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<ExchangeRate> getByNaturalKey() {
        return repository.getByValidityDate(this.register.getStartValidity(),this.register.getEndValidity());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<ExchangeRate> getResult() {
        return result;
    }

    private boolean isNewStartValidityAndEndValidityExist(boolean isNew, Optional<ExchangeRate> searchByStartAndEndValidity){
        return isNew && searchByStartAndEndValidity.isPresent();
    }
}
