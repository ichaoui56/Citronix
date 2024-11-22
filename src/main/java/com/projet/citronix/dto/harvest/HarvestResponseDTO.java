package com.projet.citronix.dto.harvest;

import com.projet.citronix.dto.field.emdb.EmdbFieldResponseDTO;
import com.projet.citronix.model.enums.SeasonType;

public record HarvestResponseDTO(
        Long id,
        SeasonType season,
        Double totalQuantity,
        EmdbFieldResponseDTO field

) {
}
