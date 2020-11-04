package com.mindware.workflow.persistence.config;

import com.mindware.workflow.core.entity.config.Caedec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperCaedec {
    void addCaedec(Caedec caedec);

    void updateCaedec(Caedec caedec);

    List<Caedec> getAllCaedec();

    Caedec getCaedecByCode(String code);

    Caedec getCaedecById(UUID id);

}
