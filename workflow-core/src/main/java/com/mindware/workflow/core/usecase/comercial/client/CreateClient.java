package com.mindware.workflow.core.usecase.comercial.client;

import com.mindware.workflow.core.entity.comercial.client.Client;
import com.mindware.workflow.core.entity.rol.Option;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.comercial.client.RepositoryClient;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateClient extends UseCaseBase<Client> implements UseCase<Client> {

    private RepositoryClient repository;

    private Client register;

    private Optional<Client> result = Optional.empty();

    private CreateClient(){}

    public static CreateClient create(RepositoryClient repository, Client register){
        CreateClient useCase = new CreateClient();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio Client' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register, "'Registro Client' no puede ser omitido");
        useCase.validateBean(register);

        return useCase;
    }

    @Override
    public void execute() {
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        Optional<Client> searchByIdCardComplete = repository.getByIdCardComplete(this.register.getIdCard(),this.register.getExtension());

        if(isNewClientAndIdCardCompleteExist(isNew,searchByIdCardComplete)){
            throw new UseCaseException(String.format("Carnet de Identidad '%s', ya se encuentra registrado",this.register.getIdCardComplete()));
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
    protected Optional<Client> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<Client> getByNaturalKey() {
        return repository.getByIdCardComplete(this.register.getIdCard(),this.register.getExtension());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<Client> getResult() {
        return result;
    }

    private boolean isNewClientAndIdCardCompleteExist(boolean isNew, Optional<Client> searchByIdCardComplete){
        return isNew && searchByIdCardComplete.isPresent();
    }
}
