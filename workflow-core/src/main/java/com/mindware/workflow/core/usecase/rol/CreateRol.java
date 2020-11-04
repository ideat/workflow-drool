package com.mindware.workflow.core.usecase.rol;

import com.mindware.workflow.core.entity.rol.Rol;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.rol.RepositoryRol;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateRol extends UseCaseBase<Rol> implements UseCase<Rol> {

    private RepositoryRol repository;

    private Rol register;

    private Optional<Rol> result = Optional.empty();

    private CreateRol(){}

    public static CreateRol create(RepositoryRol repository, Rol register){
        CreateRol useCase = new CreateRol();
        useCase.repository = Objects.requireNonNull(repository,"Repositorio Rol es requerido");
        useCase.register = Objects.requireNonNull(register,"Registro Rol es requerido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute() {
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        Optional<Rol> searchByName = repository.getRolByName(this.register.getName());

        if(isNewRolAndNameExist(isNew,searchByName)){
            throw new UseCaseException(String.format("El nombre de Rol '%s' ya se encuentra registrado",this.register.getName()));
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
    protected Optional<Rol> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<Rol> getByNaturalKey() {
        return repository.getRolByName(this.register.getName());
    }

    @Override
    protected String getMessageError() {
        return String.format("El rol con nombre '%s' ya se encuentra registrado",this.register.getName());
    }

    @Override
    public Optional<Rol> getResult() {
        return result;
    }

    private boolean isNewRolAndNameExist(boolean isNew, Optional<Rol> searchByName){
        return isNew && searchByName.isPresent();
    }
}
