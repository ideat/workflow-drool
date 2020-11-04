package com.mindware.workflow.core.usecase.config;

import com.mindware.workflow.core.entity.config.Parameter;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.config.RepositoryParameter;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateParameter extends UseCaseBase<Parameter> implements UseCase<Parameter> {

    private RepositoryParameter repository;

    private Parameter register;

    private Optional<Parameter> result = Optional.empty();

    private CreateParameter(){}

    public static CreateParameter create(RepositoryParameter repository, Parameter register){
        CreateParameter useCase = new CreateParameter();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio Parametro' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register, "'Registro' no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute(){
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        Optional<Parameter> searchByCategoryAndValue = repository.getParameterByCategoryAndValue(this.register.getCategory(), this.register.getValue());

        if (isNewParameterAndCategoryAndValueExist(isNew,searchByCategoryAndValue)){
            throw new UseCaseException(String.format("Categoria y valor '%s', '%s' ya se encuentran registrados", this.register.getCategory(), this.register.getValue()));
        }

        if (isNew){
            this.register.setId(UUID.randomUUID());
            repository.addParameter(this.register);
            result = Optional.of(this.register);
        }else{
            repository.updateParameter(this.register);
        }
    }

    @Override
    protected Optional<Parameter> getById() {
        return repository.getParameterById(this.register.getId());
    }

    @Override
    protected Optional<Parameter> getByNaturalKey() {
        return repository.getParameterByCategoryAndValue(this.register.getCategory(),this.register.getValue());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<Parameter> getResult() {
        return result;
    }

    private boolean isNewParameterAndCategoryAndValueExist(boolean isNew, Optional<Parameter> searchByCategoryAndValue){
        return isNew && searchByCategoryAndValue.isPresent();
    }
}
