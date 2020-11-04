package com.mindware.workflow.core.usecase.users;

import com.mindware.workflow.core.entity.Users;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.users.RepositoryUsers;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateUser extends UseCaseBase<Users> implements UseCase<Users> {

    private RepositoryUsers repository;

    private Users register;

    private Optional<Users> result = Optional.empty();

    private CreateUser(){}

    public static CreateUser create(RepositoryUsers repository, Users register){
        CreateUser useCase = new CreateUser();
        useCase.repository = Objects.requireNonNull(repository, "'Repositorio Usuarios' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register, "'Registro' no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute(){
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        Optional<Users> searchByIdUser = repository.getUserByIdUser(this.register.getLogin());
//        Optional<Users> searchByEmail = repository.getUserByEmail(this.register.getEmail());

        if(isNewUserAndIdUserExist(isNew,searchByIdUser)){
            throw new UseCaseException(String.format("ID Usuario '%s' ya se encuentra registrado", this.register.getLogin()));
        }
//        if(isNewUserAndEmailExist(isNew, searchByEmail)){
//            throw new UseCaseException(String.format("El correo electronico '%s' ya se encuentra registrado", this.register.getEmail()));
//        }

        if(isNew){
            this.register.setId(UUID.randomUUID());
            this.register.setDateCreation(LocalDate.now());
            this.register.setState("RESET");
            repository.addUser(this.register);
            result = Optional.of(this.register);
        }else{
            repository.updateUser(this.register);
        }
    }

    @Override
    protected Optional<Users> getById() {
        return repository.getUserById(this.register.getId());
    }

    @Override
    protected Optional<Users> getByNaturalKey() {
        return repository.getUserByIdUser(this.register.getLogin());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<Users> getResult() {
        return result;
    }

    private boolean isNewUserAndIdUserExist(boolean isNew, Optional<Users> searchByIdUser){
        return isNew && searchByIdUser.isPresent();
    }

    private boolean isNewUserAndEmailExist(boolean isNew, Optional<Users> searchByEmail){
        return isNew && searchByEmail.isPresent();
    }
}
