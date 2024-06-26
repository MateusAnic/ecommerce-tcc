package com.zprmts.tcc.ecommerce.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

import static com.zprmts.tcc.ecommerce.constants.ErrorMessage.EMPTY_FIRST_NAME;
import static com.zprmts.tcc.ecommerce.constants.ErrorMessage.EMPTY_LAST_NAME;

@Data
public class UpdateUserRequest {
    @NotBlank(message = EMPTY_FIRST_NAME)
    private String firstName;
    @NotBlank(message = EMPTY_LAST_NAME)
    private String lastName;
    private String city;
    private String address;
    private String phoneNumber;
    private String postIndex;
    private String ativo;
    private byte[] foto;
}
