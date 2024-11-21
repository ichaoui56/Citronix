package com.projet.citronix.dto.Tree;

import com.projet.citronix.annotation.checkExistance.Exist;
import com.projet.citronix.model.Field;
import com.projet.citronix.repository.FieldRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record TreeRequestDTO(
        @NotNull
        LocalDate plantationDate,

        @Exist(entity = Field.class, repository = FieldRepository.class, message = "Field does not exist.")
        Long field_id
) {
}
