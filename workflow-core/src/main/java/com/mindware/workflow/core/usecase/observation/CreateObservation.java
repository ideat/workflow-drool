package com.mindware.workflow.core.usecase.observation;

import com.mindware.workflow.core.entity.observation.Observation;
import com.mindware.workflow.core.service.data.observation.RepositoryObservation;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateObservation extends UseCaseBase<Observation> implements UseCase<Observation> {
    private RepositoryObservation repository;

    private Observation register;

    private Optional<Observation> result = Optional.empty();

    private CreateObservation(){}

    public static CreateObservation create(RepositoryObservation repository, Observation register){
        CreateObservation useCase = new CreateObservation();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio Observaciones' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register, "'Registro Observacion' no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute(){
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
    protected Optional<Observation> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<Observation> getByNaturalKey() {
        return repository.getByNumberRequest(this.register.getNumberRequest());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<Observation> getResult() {
        return result;
    }
}
