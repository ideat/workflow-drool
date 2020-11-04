package com.mindware.workflow.spring.rest.users;

import com.mindware.workflow.core.entity.Users;
import com.mindware.workflow.core.service.data.users.RepositoryUsers;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryUsers repositoryUsers;

//    @Autowired
//    BCryptPasswordEncoder encoder;

    @Test
    public void createUser(){
//        Users users = new Users();
//        users.setId(UUID.randomUUID());
//        users.setPassword(encoder.encode("123"));
//        users.setDateCreation(LocalDate.now());
//        users.setCodeOffice(27);
//        users.setEmail("user@email.com");
//        users.setLastNames("LOPEZ");
//        users.setLogin("alopez");
//        users.setNames("Arturo");
//        users.setNumDaysValidity(30);
//        users.setRol("admin");
//        users.setState("ACTIVO");
//        repositoryUsers.addUser(users);
//
//
//        assertTrue(repositoryUsers.getUserByIdUser("alopez").get().getLogin().equalsIgnoreCase(users.getLogin()));
    }
}
