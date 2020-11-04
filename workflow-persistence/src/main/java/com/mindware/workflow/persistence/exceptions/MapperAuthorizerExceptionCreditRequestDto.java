package com.mindware.workflow.persistence.exceptions;

import com.mindware.workflow.core.service.data.exceptions.dto.AuthorizerExceptionsCreditRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapperAuthorizerExceptionCreditRequestDto {

    List<AuthorizerExceptionsCreditRequestDto> getByCity(@Param("city") String city);

    List<AuthorizerExceptionsCreditRequestDto> getAll();

    List<AuthorizerExceptionsCreditRequestDto> getByUser(@Param("loginUser") String loginUser);

    List<AuthorizerExceptionsCreditRequestDto> getByCityCurrencyAmounts(@Param("city") String city,
    @Param("currency") String currency, @Param("minimum") Double miminum, @Param("maximum") Double maximum);

    List<AuthorizerExceptionsCreditRequestDto> getByCurrencyAmounts(@Param("currency") String currency,
                                                                    @Param("minimum") Double minimum, @Param("maximum") Double maximum);

    List<AuthorizerExceptionsCreditRequestDto> getByRiskType(@Param("riskType") List<String> riskType);

    List<AuthorizerExceptionsCreditRequestDto> getByRiskTypeCity(@Param("riskType") List<String> riskType,
                                                                 @Param("city") String city);
}
