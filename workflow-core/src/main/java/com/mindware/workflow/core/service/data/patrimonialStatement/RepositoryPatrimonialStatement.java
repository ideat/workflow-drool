package com.mindware.workflow.core.service.data.patrimonialStatement;

import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryPatrimonialStatement {

    void add(PatrimonialStatement patrimonialStatement);

    void delete(UUID id);

    void update(PatrimonialStatement patrimonialStatement);

    void updateCoordinates(PatrimonialStatement patrimonialStatement);

    Optional<PatrimonialStatement> getById(UUID id);

    List<PatrimonialStatement> getByIdCreditRequestApplicantCategory(UUID idCreditRequestApplicant, String category);

    List<PatrimonialStatement> getByIdCreditRequestApplicantCategoryElement(UUID idCreditRquestApplicant, String category, String elementCategory);

    List<PatrimonialStatement> getByIdCreditRequestApplicant(UUID idCreditRequestApplicant);


}
