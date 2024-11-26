package com.projet.citronix.dto.farm;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record FarmRequestDTO(
        @NotBlank(message = "Farm name cannot be blank")
        @Pattern(regexp = "^[a-zA-Z0-9 ]{3,50}$", message = "Farm name must be alphanumeric and between 3 to 50 characters")
        String name,

        @NotBlank(message = "Farm location cannot be blank")
        String location,

        @Positive(message = "Farm size must be positive")
        Double size,

        @NotNull(message = "Created date cannot be null")
        @PastOrPresent(message = "Farm creation date cannot be in the future")
        LocalDate createdDate
) {
}
