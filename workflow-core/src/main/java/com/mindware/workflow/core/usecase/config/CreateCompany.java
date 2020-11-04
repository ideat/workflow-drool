package com.mindware.workflow.core.usecase.config;

import com.mindware.workflow.core.entity.config.Company;
import com.mindware.workflow.core.service.data.config.RepositoryCompany;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateCompany extends UseCaseBase<Company> implements UseCase<Company> {

    private RepositoryCompany repository;

    private Company register;

    private Optional<Company> result = Optional.empty();

    private CreateCompany(){}

    public static CreateCompany create(RepositoryCompany repository, Company register){
        CreateCompany useCase = new CreateCompany();
        useCase.repository = Objects.requireNonNull(repository, "'Repositorio' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register, "'Registro' no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute(){
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        Optional<Company> searchByName = repository.getCompanyByName(this.register.getName());

        if(isNew){
            this.register.setId(UUID.randomUUID());
            repository.addCompany(this.register);
            result = Optional.of(this.register);
        }else {
            repository.updateCompany(this.register);
        }
    }

    @Override
    protected Optional<Company> getById() {
        return repository.getCompaneyById(this.register.getId());
    }

    @Override
    protected Optional<Company> getByNaturalKey() {
        return repository.getCompanyByName(this.register.getName());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<Company> getResult() {
        return result;
    }
}
