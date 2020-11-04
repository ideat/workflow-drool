package com.mindware.workflow.core.service.data.rol;

import com.mindware.workflow.core.entity.rol.Rol;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryRol {
    void add(Rol rol);

    void update(Rol rol);

    void delete(UUID id);

    Optional<Rol> getById(UUID id);

    Optional<Rol> getRolByName(String name);

    List<Rol> getAll();

}
