package com.zprmts.tcc.ecommerce.dto;

import com.zprmts.tcc.ecommerce.enums.CargoEnum;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

import static com.zprmts.tcc.ecommerce.constants.ErrorMessage.*;

@Data
public class RegistrationRequestAdmin {

    @NotBlank(message = EMPTY_FIRST_NAME)
    private String firstName;
    @NotBlank(message = EMPTY_LAST_NAME)
    private String lastName;
    @Size(min = 6, max = 16, message = PASSWORD_CHARACTER_LENGTH)
    private String password;
    @Size(min = 6, max = 16, message = PASSWORD2_CHARACTER_LENGTH)
    private String password2;
    @Email(message = INCORRECT_EMAIL)
    @NotBlank(message = EMAIL_CANNOT_BE_EMPTY)
    private String email;
    @NotNull
    private Set<CargoEnum> cargos;
    @NotBlank(message = CITY_CANNOT_BE_EMPTY)
    private String city;
    @NotBlank(message = ADRESS_CANNOT_BE_EMPTY)
    private String address;
    @NotBlank(message = PHONE_NUMBER_CANNOT_BE_EMPTY)
    private String phoneNumber;
    @NotBlank(message = POST_INDEX_CANNOT_BE_EMPTY)
    private String postIndex;
    private byte[] foto;
}
