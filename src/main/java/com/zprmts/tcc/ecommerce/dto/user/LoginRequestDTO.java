package com.zprmts.tcc.ecommerce.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequestDTO {
    @NotNull
    private String login;
    @NotNull
    private String senha;
}
