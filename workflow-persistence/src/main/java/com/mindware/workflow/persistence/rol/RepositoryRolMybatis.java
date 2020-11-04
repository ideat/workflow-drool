package com.mindware.workflow.persistence.rol;

import com.mindware.workflow.core.entity.rol.Rol;
import com.mindware.workflow.core.service.data.rol.RepositoryRol;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryRolMybatis implements RepositoryRol {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperRol mapper;

    RepositoryRolMybatis(){}

    public static RepositoryRol create(SqlSessionFactory sqlSessionFactory){
        RepositoryRolMybatis repository = new RepositoryRolMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void add(Rol rol) {
        mapper.add(rol);
    }

    @Override
    @Transactional
    public void update(Rol rol) {
        mapper.update(rol);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        mapper.delete(id);
    }

    @Override
    @Transactional
    public Optional<Rol> getById(UUID id) {
        return Optional.ofNullable(mapper.getById(id));
    }

    @Override
    @Transactional
    public Optional<Rol> getRolByName(String name) {
        return Optional.ofNullable(mapper.getRolByName(name));
    }

    @Override
    @Transactional
    public List<Rol> getAll() {
        return mapper.getAll();
    }
}
