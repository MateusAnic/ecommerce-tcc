package com.zprmts.tcc.ecommerce.dto;

import com.zprmts.tcc.ecommerce.domain.Perfume;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponse {
    private Long id;
    private Long quantity;
    private Perfume perfume;
}
