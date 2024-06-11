package com.zprmts.tcc.ecommerce.dto.order;

import com.zprmts.tcc.ecommerce.dto.OrderItemResponse;
import com.zprmts.tcc.ecommerce.dto.perfume.PerfumeResponse;
import com.zprmts.tcc.ecommerce.enums.StatusOrderEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private Double totalPrice;
    private LocalDate date;
    private List<OrderItemResponse> orderItemList;
    private StatusOrderEnum status;
}
