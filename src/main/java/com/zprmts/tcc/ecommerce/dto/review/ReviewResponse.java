package com.zprmts.tcc.ecommerce.dto.review;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewResponse {
    private Long id;
    private String author;
    private String message;
    private Double rating;
    private LocalDate date;
}
