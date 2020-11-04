package com.mindware.workflow.core.usecase.exceptions;

import com.mindware.workflow.core.entity.exceptions.Authorizer;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.exceptions.RepositoryAuthorizer;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateAuthorizer extends UseCaseBase<Authorizer> implements UseCase<Authorizer> {
    private RepositoryAuthorizer repository;

    private Authorizer register;

    private Optional<Authorizer> result = Optional.empty();

    private CreateAuthorizer(){}

    public static CreateAuthorizer create(RepositoryAuthorizer repository, Authorizer register){
        CreateAuthorizer useCase = new CreateAuthorizer();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio Autorizadores' es rquerido");
        useCase.register = Objects.requireNonNull(register, "Registro Autorizador es requerido" );

        useCase.validateBean(register);

        return useCase;
    }

    @Override
    public void execute() {
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        Optional<Authorizer> searchByLoginUser = repository.getByLoginUsers(this.register.getLoginUser());

        if(ifNewAuthorizerAndLoginuserExit(isNew,searchByLoginUser)){
            throw new UseCaseException(String.format("Login usuraio %s ya fue registrado",this.register.getLoginUser()));
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
    protected Optional<Authorizer> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<Authorizer> getByNaturalKey() {
        return repository.getByLoginUsers(this.register.getLoginUser());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<Authorizer> getResult() {
        return result;
    }

    private boolean ifNewAuthorizerAndLoginuserExit(boolean isNew, Optional<Authorizer> searchByLoginUser){
        return isNew && searchByLoginUser.isPresent();
    }
}
