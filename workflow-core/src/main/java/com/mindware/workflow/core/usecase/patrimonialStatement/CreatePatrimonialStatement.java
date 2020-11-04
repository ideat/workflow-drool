package com.mindware.workflow.core.usecase.patrimonialStatement;

import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.service.data.patrimonialStatement.RepositoryPatrimonialStatement;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreatePatrimonialStatement extends UseCaseBase<PatrimonialStatement> implements UseCase<PatrimonialStatement> {
    private RepositoryPatrimonialStatement repository;

    private PatrimonialStatement register;

    private Optional<PatrimonialStatement> result = Optional.empty();

    private CreatePatrimonialStatement(){}

    public static CreatePatrimonialStatement create(RepositoryPatrimonialStatement repository, PatrimonialStatement register){
        CreatePatrimonialStatement useCase = new CreatePatrimonialStatement();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio PatrimonialStatement' no puede omitirse");
        useCase.register = Objects.requireNonNull(register, "'Registro PatrimonialStatement' no puede ser omitido");

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
    protected Optional<PatrimonialStatement> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<PatrimonialStatement> getByNaturalKey() {
        return Optional.empty();
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<PatrimonialStatement> getResult() {
        return result;
    }
}
