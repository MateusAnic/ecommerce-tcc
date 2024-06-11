package com.zprmts.tcc.ecommerce.dto.order;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.zprmts.tcc.ecommerce.constants.ErrorMessage.*;

@Data
public class OrderUpdate {

    private List<Long> PerfumeList;
}
