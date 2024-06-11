package com.zprmts.tcc.ecommerce.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BaseUserResponse {
    private String email;
    private String firstName;
    private Set<CargoResponseDTO> cargos;
}
