package com.zprmts.tcc.ecommerce.dto.user;

import lombok.Data;

import java.util.Set;

@Data
public class LoginResponseDTO {
    private Integer id;
    private String email;
    private Set<CargoResponseDTO> roles;
}
