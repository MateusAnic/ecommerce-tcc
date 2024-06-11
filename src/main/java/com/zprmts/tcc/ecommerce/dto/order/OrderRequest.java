package com.zprmts.tcc.ecommerce.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private List<Long> perfumeList;
}
