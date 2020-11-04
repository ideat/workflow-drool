package com.mindware.workflow.core.usecase.legal;

import com.mindware.workflow.core.entity.legal.LegalInformation;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.legal.RepositoryLegalInformation;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateLegalInformation extends UseCaseBase<LegalInformation> implements UseCase<LegalInformation> {
    private RepositoryLegalInformation repository;

    private LegalInformation register;

    private Optional<LegalInformation> result = Optional.empty();

    private CreateLegalInformation(){}

    public static CreateLegalInformation create(RepositoryLegalInformation repository, LegalInformation register){
        CreateLegalInformation useCase = new CreateLegalInformation();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio Legal' es requerido");
        useCase.register = Objects.requireNonNull(register,"'Registro Legal' es requerido");

        useCase.validateBean(register);

        return useCase;
    }

    @Override
    public void execute() {
        super.execute();
        Optional<LegalInformation> searchByNumberRequest = repository.getByNumberRequest(this.register.getNumberRequest());


        boolean isNew = !this.searchById.isPresent();

        if(ifNewInformationLegalAndNumberRequestExist(isNew,searchByNumberRequest)){
            throw new UseCaseException(String.format("Numero solicitud '%s' ya tiene un informe legal",
                    this.register.getNumberRequest()));
        }

        if(isNew){
            this.register.setId(UUID.randomUUID());
            this.register.setReportNumber(repository.getReportNumber());

            repository.add(this.register);
            result = Optional.of(this.register);
        }else{
            repository.update(this.register);
        }

    }

    @Override
    protected Optional<LegalInformation> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<LegalInformation> getByNaturalKey() {
        return repository.getByNumberRequest(this.register.getNumberRequest());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<LegalInformation> getResult() {
        return result;
    }

    private boolean ifNewInformationLegalAndNumberRequestExist(boolean isNew, Optional<LegalInformation> searchByNumberRequest){
        return isNew && searchByNumberRequest.isPresent();
    }


}
