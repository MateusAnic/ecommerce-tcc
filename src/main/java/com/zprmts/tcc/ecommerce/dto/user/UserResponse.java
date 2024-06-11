package com.zprmts.tcc.ecommerce.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse extends BaseUserResponse {
    private Long   id;
    private String lastName;
    private String city;
    private String address;
    private String phoneNumber;
    private String postIndex;
    private String ativo;
    private byte[] foto;
}
