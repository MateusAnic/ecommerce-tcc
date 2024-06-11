package com.zprmts.tcc.ecommerce.dto.perfume;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.zprmts.tcc.ecommerce.constants.ErrorMessage.FILL_IN_THE_INPUT_FIELD;

@Data
public class PerfumeRequest {

    @NotNull(message = FILL_IN_THE_INPUT_FIELD)
    @Schema(description = "Campo para a descrição do perfume.", example = "Descrição Perfume")
    private Integer price;

    @NotBlank(message = FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String description;

    @NotBlank(message = FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String name;

    @NotBlank(message = FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String categories;

    private String foto;
}
