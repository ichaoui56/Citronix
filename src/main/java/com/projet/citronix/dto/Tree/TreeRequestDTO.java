package com.projet.citronix.dto.Tree;

import com.projet.citronix.model.Field;
import com.projet.citronix.repository.FieldRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record TreeRequestDTO(
        @NotNull
        @PastOrPresent(message = "The plantation date must be today or in the past.")
        LocalDate plantationDate,

        @NotNull
        Long field_id
) {
}
