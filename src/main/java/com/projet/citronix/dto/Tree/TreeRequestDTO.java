package com.projet.citronix.dto.Tree;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record TreeRequestDTO(
        @NotNull(message = "Plantation date is required")
        @PastOrPresent(message = "The plantation date must be today or in the past.")
        LocalDate plantationDate,

        @NotNull(message = "Field ID is required")
        Long field_id
) {
}
