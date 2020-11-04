package com.mindware.workflow.core.usecase.config;

import com.mindware.workflow.core.entity.config.TemplateForm;
import com.mindware.workflow.core.service.data.config.RepositoryTemplateForms;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateTemplateForms extends UseCaseBase<TemplateForm> implements UseCase<TemplateForm> {

    private RepositoryTemplateForms repository;

    private TemplateForm register;

    private Optional<TemplateForm> result = Optional.empty();

    private CreateTemplateForms(){

    }

    public static CreateTemplateForms create(RepositoryTemplateForms repository, TemplateForm register){
        CreateTemplateForms useCase = new CreateTemplateForms();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio TemplateForm' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register, "'Registro TemplateForm' no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute(){
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        Optional<TemplateForm> searchByName = repository.getByNameCategory(this.register.getName(), this.register.getCategory());

        if(isNew && !searchByName.isPresent()){
            this.register.setId(UUID.randomUUID());
            repository.add(this.register);
            result = Optional.of(this.register);
        }else{
            repository.update(this.register);
        }
    }

    @Override
    protected Optional<TemplateForm> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<TemplateForm> getByNaturalKey() {
        return repository.getByNameCategory(this.register.getName(), this.register.getCategory());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<TemplateForm> getResult() {
        return this.result;
    }
}
