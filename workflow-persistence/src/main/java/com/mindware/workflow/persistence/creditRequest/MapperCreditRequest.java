package com.mindware.workflow.persistence.creditRequest;

import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperCreditRequest {

    void addCreditRequest(CreditRequest creditRequest);

    void updateCreditRequest(CreditRequest creditRequest);

    List<CreditRequest> getAllCreditRequest();

    List<CreditRequest> getCrediRequestByLoginUser(@Param("loginUser") String loginUser);

    List<CreditRequest> getCreditRequestByIdOffice(@Param("idOffice") int idOffice);

    CreditRequest getCreditRequestById(@Param("id") UUID id);

    CreditRequest getCreditRequestByNumberRequest(@Param("numberRequest") Integer numberRequest);

    void updateCompanySizeIndicator(CreditRequest creditRequest);
}
