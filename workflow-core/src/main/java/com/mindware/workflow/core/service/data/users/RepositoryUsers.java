package com.mindware.workflow.core.service.data.users;

import com.mindware.workflow.core.entity.Users;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryUsers {
    void addUser(Users users);

    void updateUser(Users users);

    void updatePassword(Users users);

    List<Users> getAllUsers();

    Optional<Users> getUserById(UUID id);

    Optional<Users> getUserByIdUser(String login);

    Optional<Users> getUserByEmail(String email);

    List<Users> getByRol(String rol);

    List<Users> getByCodeOffice(Integer codeOffice);
}
