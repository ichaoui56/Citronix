package com.projet.citronix.dto.farm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

public record FarmRequestDTO(
        @NotBlank String name,
        @NotBlank String location,
        @Positive Double size,
        @NotNull LocalDate createdDate
) {
}

