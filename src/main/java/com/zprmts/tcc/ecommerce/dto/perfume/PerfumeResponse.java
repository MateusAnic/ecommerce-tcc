package com.zprmts.tcc.ecommerce.dto.perfume;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfumeResponse {
    private Long   id;
    private String description;
    private String name;
    private Double price;
    private String categories;
    private String foto;
    private Double perfumeRating;
}
