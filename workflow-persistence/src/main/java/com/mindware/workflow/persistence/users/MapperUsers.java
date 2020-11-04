package com.mindware.workflow.persistence.users;

import com.mindware.workflow.core.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MapperUsers {

    void addUser(Users users);

    void updateUser(Users users);

    void updatePassword(Users users);

    List<Users> getAllUsers();

    Users getUserById(@Param("id") UUID id);

    Users getUserByIdUser(@Param("login") String login);

    Users getUserByEmail(@Param("email") String email);

    List<Users> getByRol(@Param("rol") String rol);

    List<Users> getByCodeOffice(@Param("codeOffice") Integer codeOffice);
}
