package com.mindware.workflow.core.service.data.email;

import com.mindware.workflow.core.entity.email.Mail;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryMail {
    void add(Mail mail);

    void update(Mail mail);

    List<Mail> getByNumberRequest(Integer numberRequest);

    Optional<Mail> getById(UUID id);
}
