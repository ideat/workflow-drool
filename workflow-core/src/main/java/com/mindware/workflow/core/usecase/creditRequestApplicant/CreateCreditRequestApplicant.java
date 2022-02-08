package com.mindware.workflow.core.usecase.creditRequestApplicant;

import com.mindware.workflow.core.entity.CreditRequestApplicant;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.creditRequestApplicant.RepositoryCreditRequestApplicant;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;


import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateCreditRequestApplicant extends UseCaseBase<CreditRequestApplicant> implements UseCase<CreditRequestApplicant> {

    private RepositoryCreditRequestApplicant repository;

    private CreditRequestApplicant register;

    private Optional<CreditRequestApplicant> result = Optional.empty();

    private CreateCreditRequestApplicant(){}

    public static CreateCreditRequestApplicant create(RepositoryCreditRequestApplicant repository, CreditRequestApplicant register){
        CreateCreditRequestApplicant useCase = new CreateCreditRequestApplicant();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio' es requerido");
        useCase.register = Objects.requireNonNull(register,"'Registro' es requerido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute(){
        super.execute();
        Optional<CreditRequestApplicant> searchByIdCreditRequetIdApplicantTypeRelation = repository.getCreditRequestApplicantByNumberApplicantAndNumberCreditRequestAndTypeRelation(
              this.register.getNumberRequest(), this.register.getNumberApplicant(), this.register.getTypeRelation());
        boolean isNew = !this.searchById.isPresent();

        if (searchByIdCreditRequetIdApplicantTypeRelation.isPresent()){
            throw  new UseCaseException(String.format("Solicitud '%s', Aplicante '%s' y Relacion '%s' ya se encuentra registrado",
                    this.register.getNumberRequest(), this.register.getNumberApplicant(), this.register.getTypeRelation()));
        }
        if(isNew) {
            this.register.setId(UUID.randomUUID());
            repository.add(this.register);
            result = Optional.of(this.register);
        }else {
            repository.update(this.register);
        }
    }

    @Override
    protected Optional<CreditRequestApplicant> getById() {
        return repository.getCreditRequestApplicantbyId(this.register.getId());
    }

    @Override
    protected Optional<CreditRequestApplicant> getByNaturalKey() {
        return repository.getCreditRequestApplicantByNumberApplicantAndNumberCreditRequestAndTypeRelation(this.register.getNumberRequest(),this.register.getNumberApplicant(),this.register.getTypeRelation());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<CreditRequestApplicant> getResult() {
        return result;
    }


}
