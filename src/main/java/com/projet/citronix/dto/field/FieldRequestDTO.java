package com.projet.citronix.dto.field;

import com.projet.citronix.annotation.checkExistance.Exist;
import com.projet.citronix.model.Farm;
import com.projet.citronix.repository.FarmRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FieldRequestDTO(

        @NotNull String name,

        @Positive Double area,

        @Exist(entity = Farm.class, repository = FarmRepository.class) Long farm_id
) {
}

