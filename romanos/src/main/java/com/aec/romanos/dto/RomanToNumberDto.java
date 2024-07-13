package com.aec.romanos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) representing Roman number input data.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Roman number representation with input data.")
public class RomanToNumberDto {

    @NotBlank(message = "{roman.blank}")
    @NotNull(message = "{roman.null}")
    @Pattern(regexp = "^(?=[MDCLXVI])M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$", message = "{roman.invalid}")
    private String roman;

}
