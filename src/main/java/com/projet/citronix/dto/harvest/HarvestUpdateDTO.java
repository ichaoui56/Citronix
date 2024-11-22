package com.projet.citronix.dto.harvest;

import java.time.LocalDate;

public record HarvestUpdateDTO(
        Long fieldId,
        LocalDate date
) {
}
