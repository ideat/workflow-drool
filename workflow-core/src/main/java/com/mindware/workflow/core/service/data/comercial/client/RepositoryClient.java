package com.mindware.workflow.core.service.data.comercial.client;

import com.mindware.workflow.core.entity.comercial.client.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryClient {
    void add(Client client);

    void update(Client client);

    Optional<Client> getByIdCardComplete(String idCard, String extension);

    Optional<Client> getById(UUID id);

    List<Client> getByUser(String loginUser);

    List<Client> getAll();
}
