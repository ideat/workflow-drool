package com.mindware.workflow.persistence.users;

import com.mindware.workflow.core.entity.Users;
import com.mindware.workflow.core.service.data.users.RepositoryUsers;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryUsersMybatis implements RepositoryUsers {

    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapperUsers mapper;

    RepositoryUsersMybatis(){}

    public static RepositoryUsers create(SqlSessionFactory sqlSessionFactory){
        RepositoryUsersMybatis repository = new RepositoryUsersMybatis();
        repository.sqlSessionFactory = sqlSessionFactory;
        return repository;
    }

    @Override
    @Transactional
    public void addUser(Users users) {
        mapper.addUser(users);
    }

    @Override
    public void updateUser(Users users) {
        mapper.updateUser(users);
    }

    @Override
    @Transactional
    public void updatePassword(Users users) {

        mapper.updatePassword(users);
    }

    @Override
    public List<Users> getAllUsers() {
        return mapper.getAllUsers();
    }

    @Override
    public Optional<Users> getUserById(UUID id) {
        return Optional.ofNullable(mapper.getUserById(id));
    }

    @Override
    public Optional<Users> getUserByIdUser(String login) {
        return Optional.ofNullable(mapper.getUserByIdUser(login));
    }

    @Override
    public Optional<Users> getUserByEmail(String email) {
        return Optional.ofNullable(mapper.getUserByEmail(email));
    }

    @Override
    public List<Users> getByRol(String rol) {
        return mapper.getByRol(rol);
    }

    @Override
    public List<Users> getByCodeOffice(Integer codeOffice) {
        return mapper.getByCodeOffice(codeOffice);
    }
}
