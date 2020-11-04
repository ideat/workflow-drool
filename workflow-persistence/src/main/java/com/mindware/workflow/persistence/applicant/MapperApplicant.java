package com.mindware.workflow.persistence.applicant;

import com.mindware.workflow.core.entity.Applicant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MapperApplicant {

    void addApplicant(Applicant applicant);

    void updateApplicant(Applicant applicant);

    List<Applicant> getAllApplicant();

    List<Applicant> getApplicantByLoginUser(@Param("loginUser") String loginUser);

    Applicant getApplicantById(@Param("id") UUID id);

    Applicant getApplicantByIdCard(@Param("idCard") String idCard);

    Applicant getApplicantByNumberApplicant(@Param("numberApplicant") Integer numberApplicant);

}
