package com.mindware.workflow.core.usecase.workUpReview;

import com.mindware.workflow.core.entity.workupReview.WorkUpReview;
import com.mindware.workflow.core.service.data.workUpReview.RepositoryWorkUpReview;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateWorkUpReview extends UseCaseBase<WorkUpReview> implements UseCase<WorkUpReview> {
    private RepositoryWorkUpReview repository;

    private WorkUpReview register;

    private Optional<WorkUpReview> result = Optional.empty();

    private CreateWorkUpReview(){}

    public static CreateWorkUpReview create(RepositoryWorkUpReview repository, WorkUpReview register){
        CreateWorkUpReview useCase = new CreateWorkUpReview();
        useCase.repository = Objects.requireNonNull(repository, "'Repositorio Observaciones' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register, "'Registro' no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute(){
        super.execute();
        boolean isNew = !this.searchById.isPresent();

        if(isNew){
            this.register.setId(UUID.randomUUID());
            repository.addWorkUpReview(this.register);
            result = Optional.of(this.register);
        }else{
            repository.updateWorkUpReview(this.register);
        }
    }

    @Override
    protected Optional<WorkUpReview> getById() {
        return repository.getWorkUpReviewById(this.register.getId());
    }

    @Override
    protected Optional<WorkUpReview> getByNaturalKey() {
        return Optional.empty();
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<WorkUpReview> getResult() {
        return result;
    }
}
