package com.mindware.workflow.persistence.email;

import com.mindware.workflow.core.entity.email.Mail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import java.util.UUID;

@Mapper
public interface MapperMail {

    void add(Mail mail);

    void update(Mail mail);

    List<Mail> getByNumberRequest(@Param("numberRequest") Integer numberRequest);

    Mail getById(@Param("id") UUID id);
}
