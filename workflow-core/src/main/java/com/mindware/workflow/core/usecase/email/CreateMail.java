package com.mindware.workflow.core.usecase.email;

import com.mindware.workflow.core.entity.email.Mail;
import com.mindware.workflow.core.service.data.email.RepositoryMail;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateMail extends UseCaseBase<Mail> implements UseCase<Mail> {

    private RepositoryMail repository;

    private Mail register;

    private Optional<Mail> result = Optional.empty();

    private CreateMail(){}

    public static  CreateMail create(RepositoryMail repository, Mail register){
        CreateMail useCase = new CreateMail();
        useCase.repository = Objects.requireNonNull(repository,"Repositorio Mail es requerido");
        useCase.register = Objects.requireNonNull(register,"Registro Mail es requerido");

        useCase.validateBean(register);

        return useCase;
    }

    @Override
    public void execute() {
        super.execute();
        boolean isNew = !this.searchById.isPresent();

        if(isNew){
            this.register.setId(UUID.randomUUID());
            repository.add(this.register);
            result = Optional.of(this.register);
        }else{
            repository.update(this.register);
        }
    }

    @Override
    protected Optional<Mail> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<Mail> getByNaturalKey() {
        return Optional.empty();
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<Mail> getResult() {
        return result;
    }
}
