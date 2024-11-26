package com.projet.citronix.dto.field;

import jakarta.validation.constraints.*;

public record FieldRequestDTO(
        @NotBlank(message = "Field name cannot be blank")
        @Size(min = 3, max = 50, message = "Field name must be between 3 and 50 characters")
        String name,

        @NotNull(message = "Field area is required")
        @Positive(message = "Field area must be a positive value")
        Double area,

        @NotNull(message = "Farm ID is required")
        @Positive(message = "Farm ID must be a positive value")
        Long farm_id
) {
}
