package com.projet.citronix.dto.harvest;

import java.time.LocalDate;

public record HarvestRequestDTO(
        Long fieldId,
        LocalDate date
) {
}
