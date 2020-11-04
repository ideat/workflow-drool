package com.mindware.workflow.core.usecase.cityProvince;

import com.mindware.workflow.core.entity.config.CityProvince;
import com.mindware.workflow.core.entity.rol.Option;
import com.mindware.workflow.core.exception.UseCaseException;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.core.usecase.UseCaseBase;
import com.mindware.workflow.core.service.data.cityProvince.RepositoryCityProvince;
import com.mindware.workflow.core.usecase.cashFlow.CreateCashFlow;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreateCityProvince extends UseCaseBase<CityProvince> implements UseCase<CityProvince> {

    private RepositoryCityProvince repository;

    private CityProvince register;

    private Optional<CityProvince> result = Optional.empty();

    private CreateCityProvince(){}

    public static CreateCityProvince create(RepositoryCityProvince repository,CityProvince register){
        CreateCityProvince useCase = new CreateCityProvince();
        useCase.repository = Objects.requireNonNull(repository,"'Repositorio RepositoryCityProvince' no puede ser omitido");
        useCase.register = Objects.requireNonNull(register,"'Registro CityProvince', no puede ser omitido");

        useCase.validateBean(register);
        return useCase;
    }

    @Override
    public void execute() {
        super.execute();
        boolean isNew = !this.searchById.isPresent();

        Optional<CityProvince> searchByCity = repository.getByCity(this.register.getCity());
        Optional<CityProvince> searchByExternalCode = repository.getByExternalCode(this.register.getExternalCode());

        if(ifNewCityProvinceAndCityExist(isNew,searchByCity)){
            throw  new UseCaseException(String.format("Nombre ciudad '%s' ya se encuentra registrada",this.register.getCity()));
        }

        if(ifNewCityProvinceAndExternalCodeExit(isNew,searchByExternalCode)){
            throw new UseCaseException(String.format("Codigo Externo '%s' ya se encuentra registrado",this.register.getExternalCode()));
        }

        if(isNew){
            register.setId(UUID.randomUUID());
            repository.add(this.register);
            result = Optional.of(this.register);
        }else{
            repository.update(this.register);
        }

    }

    @Override
    protected Optional<CityProvince> getById() {
        return repository.getById(this.register.getId());
    }

    @Override
    protected Optional<CityProvince> getByNaturalKey() {
        return repository.getByCity(this.register.getCity());
    }

    @Override
    protected String getMessageError() {
        return null;
    }

    @Override
    public Optional<CityProvince> getResult() {
        return result;
    }

    private boolean ifNewCityProvinceAndCityExist(boolean isNew, Optional<CityProvince> searchByCity){
        return isNew && searchByCity.isPresent();
    }

    private boolean ifNewCityProvinceAndExternalCodeExit(boolean isNew, Optional<CityProvince> searchByExternalCode){
        return isNew && searchByExternalCode.isPresent();
    }
}
