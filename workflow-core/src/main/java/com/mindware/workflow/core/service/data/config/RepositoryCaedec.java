package com.mindware.workflow.core.service.data.config;

import com.mindware.workflow.core.entity.config.Caedec;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryCaedec {
    void addCaedec(Caedec caedec);

    void updateCaedec(Caedec caedec);

    List<Caedec> getAllCaedec();

    Optional<Caedec> getCaedecByCode(String code);

    Optional<Caedec> getCaedecById(UUID id);
}
