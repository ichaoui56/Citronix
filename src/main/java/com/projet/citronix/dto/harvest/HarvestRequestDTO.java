package com.projet.citronix.dto.harvest;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record HarvestRequestDTO(
        @NotNull(message = "Field ID is required")
        @Positive(message = "Field ID must be a positive value")
        Long fieldId,

        @NotNull(message = "Harvest date is required")
        @Pattern(
                regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$",
                message = "Harvest date must be in the format YYYY-MM-DD"
        )
        LocalDate date
) {
}
