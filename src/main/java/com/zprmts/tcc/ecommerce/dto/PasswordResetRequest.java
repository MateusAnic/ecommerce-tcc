package com.zprmts.tcc.ecommerce.dto;

import lombok.Data;

import javax.validation.constraints.Size;

import static com.zprmts.tcc.ecommerce.constants.ErrorMessage.PASSWORD2_CHARACTER_LENGTH;
import static com.zprmts.tcc.ecommerce.constants.ErrorMessage.PASSWORD_CHARACTER_LENGTH;

@Data
public class PasswordResetRequest {

    @Size(min = 6, max = 16, message = PASSWORD_CHARACTER_LENGTH)
    private String password;

    @Size(min = 6, max = 16, message = PASSWORD2_CHARACTER_LENGTH)
    private String password2;
}
