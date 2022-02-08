package com.mindware.workflow.core.usecase.historyChangeResponsible;

import com.mindware.workflow.core.entity.historyChangeResponsible.HistoryChangeResponsible;
import com.mindware.workflow.core.service.data.historyChangeResponsible.RepositoryHistoryChangeResponsible;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateHistoryChangeResponsible extends UseCaseBase<HistoryChangeResponsible> implements UseCase<HistoryChangeResponsible> {
    private RepositoryHistoryChangeResponsible repository;

    private HistoryChangeResponsible register;

    private Optional<HistoryChangeResponsible> result = Optional.empty();

    private CreateHistoryChangeResponsible(){}

    public static CreateHistoryChangeResponsible create(RepositoryHistoryChangeResponsible repository, HistoryChangeResponsible register){
        CreateHistoryChangeResponsible useCase = new CreateHistoryChangeResponsible();
        useCase.repository = Objects.requireNonNull(repository, "'Repositorio Historico de habiltacion' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register,"'Registro Historico de habilitacion' no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute(){
        super.execute();

        this.register.setId(UUID.randomUUID());
        repository.add(this.register);
        result = Optional.of(this.register);

    }

    @Override
    public Optional<HistoryChangeResponsible> getResult() {
        return result;
    }

    @Override
    protected Optional<HistoryChangeResponsible> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<HistoryChangeResponsible> getByNaturalKey() {
        return Optional.empty();
    }

    @Override
    protected String getMessageError() {
        return null;
    }
}
