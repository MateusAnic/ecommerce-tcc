package com.zprmts.tcc.ecommerce.controller;

import com.zprmts.tcc.ecommerce.domain.User;
import com.zprmts.tcc.ecommerce.dto.PasswordResetRequest;
import com.zprmts.tcc.ecommerce.dto.user.LoginRequestDTO;
import com.zprmts.tcc.ecommerce.exception.RegraDeNegocioException;
import com.zprmts.tcc.ecommerce.security.TokenService;
import com.zprmts.tcc.ecommerce.service.Impl.AuthenticationServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@Tag(name = "Login")
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public String auth(@RequestBody @Valid LoginRequestDTO loginRequestDTO) throws RegraDeNegocioException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getLogin(),
                        loginRequestDTO.getSenha()
                );

        Authentication authentication =
                authenticationManager.authenticate(
                        usernamePasswordAuthenticationToken);

        User usuarioValidado = (User) authentication.getPrincipal();
        if (usuarioValidado.getAtivo().equals("N")) {
            throw new RegraDeNegocioException("Usuário inativo");
        }
        return tokenService.generateToken(usuarioValidado);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> passwordReset(PasswordResetRequest passwordResetRequest) throws Exception {
        return new ResponseEntity<>(authenticationService.passwordReset(passwordResetRequest), HttpStatus.OK);
    }
}
