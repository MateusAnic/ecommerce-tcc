package com.zprmts.tcc.ecommerce.controller;

import com.zprmts.tcc.ecommerce.dto.RegistrationRequest;
import com.zprmts.tcc.ecommerce.dto.RegistrationRequestAdmin;
import com.zprmts.tcc.ecommerce.service.Impl.AuthenticationServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
@Tag(name = "Register")
public class RegistrationController {

    private final AuthenticationServiceImpl authenticationService;

    @PostMapping
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationRequest user) throws Exception {
        return new ResponseEntity<>(authenticationService.registerUser(user), HttpStatus.OK);
    }

    @PostMapping("/admin")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationRequestAdmin user) throws Exception {
        return new ResponseEntity<>(authenticationService.registerUserAdmin(user), HttpStatus.OK);
    }

}
