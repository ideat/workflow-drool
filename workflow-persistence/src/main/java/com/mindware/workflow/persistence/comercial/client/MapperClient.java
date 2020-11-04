package com.mindware.workflow.persistence.comercial.client;

import com.mindware.workflow.core.entity.comercial.client.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MapperClient {
    void add(Client client);

    void update(Client client);

    Client getByIdCardComplete(@Param("idCard") String idCard, @Param("extension") String extension);

    Client getById(@Param("id") UUID id);

    List<Client> getByUser(@Param("loginUser") String loginUser);

    List<Client> getAll();
}
