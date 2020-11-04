package com.mindware.workflow.core.usecase.stageHistory;

import com.mindware.workflow.core.entity.stageHistory.StageHistory;
import com.mindware.workflow.core.service.data.stageHistory.RepositoryStageHistory;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateStageHistory extends UseCaseBase<StageHistory> implements UseCase<StageHistory> {
    private RepositoryStageHistory repository;

    private StageHistory register;

    private Optional<StageHistory> result = Optional.empty();

    public static CreateStageHistory create(RepositoryStageHistory repository, StageHistory register){
        CreateStageHistory useCase = new CreateStageHistory();
        useCase.repository = Objects.requireNonNull(repository, "'Repositorio Historico Etapas' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register,"'Registro Historico de Etapas' no puede ser omitido");

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
    protected Optional<StageHistory> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<StageHistory> getByNaturalKey() {
        return Optional.empty();
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<StageHistory> getResult() {
        return result;
    }


}
