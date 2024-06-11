package com.zprmts.tcc.ecommerce.dto.review;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

import static com.zprmts.tcc.ecommerce.constants.ErrorMessage.FILL_IN_THE_INPUT_FIELD;

@Data
public class ReviewRequest {

    @NotBlank(message = FILL_IN_THE_INPUT_FIELD)
    private String message;

    @NotNull(message = "Choose perfume rating")
    private Double rating;
}