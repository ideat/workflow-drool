package com.mindware.workflow.core.usecase.config;

import com.mindware.workflow.core.entity.config.WorkflowProduct;
import com.mindware.workflow.core.service.data.config.RepositoryWorkflowProduct;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateWorkflowProduct extends UseCaseBase<WorkflowProduct> implements UseCase<WorkflowProduct> {

    private RepositoryWorkflowProduct repository;

    private WorkflowProduct register;

    private Optional<WorkflowProduct> result = Optional.empty();

    private CreateWorkflowProduct(){}

    public static CreateWorkflowProduct create(RepositoryWorkflowProduct repository, WorkflowProduct register){
        CreateWorkflowProduct useCase = new CreateWorkflowProduct();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio WorkflowCredit. no puede ser omitido");
        useCase.register = Objects.requireNonNull(register, "'Registro Workflow' no puede ser omitido");

        useCase.validateBean(register);

        return useCase;
    }

    @Override
    public void execute() {
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        Optional<WorkflowProduct> searchByCode = repository.getByTypeCreditAndObject(this.register.getCodeTypeCredit(),this.register.getCodeObjectCredit());

        if(isNew){
            this.register.setId(UUID.randomUUID());
            repository.add(this.register);
            result = Optional.of(this.register);
        }else{
            repository.update(this.register);
        }
    }

    @Override
    protected Optional<WorkflowProduct> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<WorkflowProduct> getByNaturalKey() {
        return repository.getByTypeCreditAndObject(this.register.getCodeTypeCredit(),this.register.getCodeObjectCredit());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<WorkflowProduct> getResult() {
        return result;
    }
}
