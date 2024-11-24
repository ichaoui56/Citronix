package com.projet.citronix.dto.field;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FieldRequestDTO(

        @NotNull String name,
        @Positive Double area,
        @NotNull Long farm_id
) {
}

