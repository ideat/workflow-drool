package com.mindware.workflow.core.usecase.config;

import com.mindware.workflow.core.entity.config.Caedec;
import com.mindware.workflow.core.service.data.config.RepositoryCaedec;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateCaedec extends UseCaseBase<Caedec> implements UseCase<Caedec> {

    private RepositoryCaedec repository;

    private Caedec register;


    private Optional<Caedec> result = Optional.empty();

    private CreateCaedec(){}

    public static CreateCaedec create(RepositoryCaedec repository, Caedec register){
        CreateCaedec useCase = new CreateCaedec();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio Caedec' no pue ser omitido");
        useCase.register = Objects.requireNonNull(register, "'Registro Caedec' no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }


    @Override
    public void execute(){
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        Optional<Caedec> searchByCode = repository.getCaedecByCode(this.register.getCode());

        if(isNew){
            this.register.setId(UUID.randomUUID());
            repository.addCaedec(this.register);
            result = Optional.of(this.register);
        }else{
            repository.updateCaedec(this.register);
        }
    }

    @Override
    protected Optional<Caedec> getById() {
        return repository.getCaedecById(this.register.getId());
    }

    @Override
    protected Optional<Caedec> getByNaturalKey() {
        return repository.getCaedecByCode(this.register.getCode());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<Caedec> getResult() {
        return result;
    }
}
