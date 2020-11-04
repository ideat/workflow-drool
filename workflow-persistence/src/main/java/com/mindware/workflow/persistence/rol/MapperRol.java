package com.mindware.workflow.persistence.rol;

import com.mindware.workflow.core.entity.rol.Rol;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperRol {
    void add(Rol rol);

    void update(Rol rol);

    void delete(@Param("id") UUID id);

    Rol getById(@Param("id") UUID id);

    Rol getRolByName(@Param("name") String name);

    List<Rol> getAll();
}
