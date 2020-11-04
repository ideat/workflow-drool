package com.mindware.workflow.core.usecase.templateObservation;

import com.mindware.workflow.core.entity.templateObservation.TemplateObservation;
import com.mindware.workflow.core.service.data.templateObservation.RepositoryTemplateObservation;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateTemplateObservation extends UseCaseBase<TemplateObservation> implements UseCase<TemplateObservation> {

    private RepositoryTemplateObservation repository;

    private TemplateObservation register;

    private Optional<TemplateObservation> result = Optional.empty();

    private CreateTemplateObservation(){}

    public static CreateTemplateObservation create(RepositoryTemplateObservation repository, TemplateObservation register){
        CreateTemplateObservation useCase = new CreateTemplateObservation();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio TemplateObservation' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register,"'Registro TemplateObservation' no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute(){
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        if (isNew){
            this.register.setId(UUID.randomUUID());
            repository.add(this.register);
            result = Optional.of(this.register);
        }else{
            repository.update(this.register);
        }
    }

    @Override
    protected Optional<TemplateObservation> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<TemplateObservation> getByNaturalKey() {
        return Optional.empty();
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<TemplateObservation> getResult() {
        return result;
    }
}
