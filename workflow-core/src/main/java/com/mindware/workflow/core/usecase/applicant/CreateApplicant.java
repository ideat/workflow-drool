package com.mindware.workflow.core.usecase.applicant;

import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.applicant.RepositoryApplicant;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateApplicant extends UseCaseBase<Applicant> implements UseCase<Applicant> {

    private RepositoryApplicant repository;

    private Applicant register;

    private Optional<Applicant> result = Optional.empty();

    private CreateApplicant(){}

    public static CreateApplicant create(RepositoryApplicant repository, Applicant register){
        CreateApplicant useCase = new CreateApplicant();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio aplicante' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register,"'Registro' no puede ser omitido");

        useCase.validateBean(register);

        return useCase;
    }

    @Override
    public void execute(){
        super.execute();
        boolean isNew = !this.searchById.isPresent();

        Optional<Applicant> searchByIdCard = repository.getApplicantByIdCard(this.register.getIdCard());

        if (ifNewApplicantAndIdCardExist(isNew,searchByIdCard) || existIdCardInOtherApplicant(this.register.getId(),searchByIdCard) ){
            throw new UseCaseException((String.format("Carnet identidad '%s' ya se encuentra registrado",this.register.getIdCard())));
        }

        if (isNew){
            this.register.setId(UUID.randomUUID());
            repository.addApplicant(this.register);
            result = Optional.of(this.register);
        }else{
            repository.updateApplicant(this.register);
        }
    }

    @Override
    protected Optional<Applicant> getById() {
        return repository.getApplicantById(this.register.getId());
    }

    @Override
    protected Optional<Applicant> getByNaturalKey() {
        return repository.getApplicantByIdCard(this.register.getIdCard());
    }

    @Override
    protected String getMessageError() {
        return String.format("Carnet identidad '%s' ya se encuentra registrado",this.register.getIdCard());
    }

    @Override
    public Optional<Applicant> getResult() {
        return result;
    }

    private boolean ifNewApplicantAndIdCardExist(boolean isNew, Optional<Applicant> searchByIdCard){
        return isNew && searchByIdCard.isPresent();
    }

    private boolean existIdCardInOtherApplicant(UUID id, Optional<Applicant> searchByIdCard){
        return searchByIdCard.isPresent() && !searchByIdCard.get().getId().equals(id);
    }
}
