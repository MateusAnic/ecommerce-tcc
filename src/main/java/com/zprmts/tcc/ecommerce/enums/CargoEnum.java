package com.zprmts.tcc.ecommerce.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CargoEnum {
    ROLE_ADMIN(1),
    ROLE_USUARIO(2);

    private final Integer valor;
}
