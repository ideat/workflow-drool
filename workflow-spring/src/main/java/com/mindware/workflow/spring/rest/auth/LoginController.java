package com.mindware.workflow.spring.rest.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", produces = { "application/json" }, consumes = { "application/json" })
public class LoginController {
    public static final String LOGIN_URL ="/auth/login";


}
