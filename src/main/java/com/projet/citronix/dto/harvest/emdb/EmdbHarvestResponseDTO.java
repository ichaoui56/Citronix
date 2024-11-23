package com.projet.citronix.dto.harvest.emdb;

import com.projet.citronix.dto.field.emdb.EmdbFieldResponseDTO;
import com.projet.citronix.model.enums.SeasonType;

public record EmdbHarvestResponseDTO(
        Long id,
        SeasonType season,
        Double totalQuantity
) {
}
