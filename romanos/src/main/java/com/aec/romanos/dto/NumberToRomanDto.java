package com.aec.romanos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) representing Arabic number input data.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Arabic number representation with input data.")
public class NumberToRomanDto {

    @Min(value = 1, message = "{min.number.pattern}")
    @Max(value = 3999, message = "{max.number.pattern}")
    @NotNull(message = "{number.null}")
    private Integer number;

}
