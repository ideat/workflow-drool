package com.mindware.workflow.core.usecase.office;

import com.mindware.workflow.core.entity.config.Office;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.service.data.office.RepositoryOffice;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateOffice extends UseCaseBase<Office> implements UseCase<Office> {

    private RepositoryOffice repository;

    private Office register;

    private Optional<Office> result = Optional.empty();

    private CreateOffice(){}

    public static CreateOffice create(RepositoryOffice repository, Office register){
        CreateOffice useCase = new CreateOffice();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio Office' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register,"'Registro' no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute() {
        super.execute();
        boolean isNew = !this.searchById.isPresent();
        Optional<Office> searchByInternalCode = repository.getOfficeByInternalCode(this.register.getInternalCode());

        if(isNewOfficeAndInternalCodeExist(isNew,searchByInternalCode) || existInternalCodeInOtherOffice(this.register.getId(),searchByInternalCode)){
            throw new UseCaseException(String.format("Codigo interno de sucursal '%s' ya se encuentra registrado ", this.register.getInternalCode()));
        }

        if (isNew){

            this.register.setId(UUID.randomUUID());
            if (this.register.getTypeOffice().toUpperCase().equals("SUCURSAL") || this.register.getTypeOffice().toUpperCase().equals("CENTRAL")) {
                this.register.setIdRoot(this.register.getId());
            }else {
                this.register.setIdRoot(this.register.getIdRoot());
            }
            repository.addOffice(this.register);
            result = Optional.of(this.register);
        }else{
//            register.setId(search.get().getId());
            repository.updateOffice(this.register);
        }

    }

    @Override
    protected Optional<Office> getById() {
        return repository.getOfficeById(this.register.getId());
    }

    @Override
    protected Optional<Office> getByNaturalKey() {
        return repository.getOfficeByInternalCode(this.register.getInternalCode());
    }

    @Override
    protected String getMessageError() {
        return String.format("La oficina con codigo '%s', ya se encuentra registrada ", this.register.getInternalCode());
    }

    @Override
    public Optional<Office> getResult() {
        return result;
    }

    private boolean isNewOfficeAndInternalCodeExist(boolean isNew, Optional<Office> searchByInternalCode){
        return isNew && searchByInternalCode.isPresent();
    }

    private boolean existInternalCodeInOtherOffice(UUID id, Optional<Office> searchByInternalCode){
        return searchByInternalCode.isPresent() && !searchByInternalCode.get().getId().equals(id);
    }
}
