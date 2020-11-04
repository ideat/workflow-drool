package com.mindware.workflow.persistence.patrimonialStatement;

import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperPatrimonialStatement {
    void add(PatrimonialStatement patrimonialStatement);

    void delete(@Param("id") UUID id);

    void update(PatrimonialStatement patrimonialStatement);

    PatrimonialStatement getById(@Param("id") UUID id);

    List<PatrimonialStatement> getByIdCreditRequestApplicantCategory(@Param("idCreditRequestApplicant") UUID idCreditRequestApplicant, @Param("category")String category);

    List<PatrimonialStatement> getByIdCreditRequestApplicantCategoryElement(@Param("idCreditRequestApplicant") UUID idCreditRquestApplicant,
                                                                            @Param("category")String category, @Param("elementCategory") String elementCategory);

    List<PatrimonialStatement> getByIdCreditRequestApplicant(@Param("idCreditRequestApplicant") UUID idCreditRequestApplicant);

}
