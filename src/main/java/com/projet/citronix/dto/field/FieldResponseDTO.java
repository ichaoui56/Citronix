package com.projet.citronix.dto.field;

import com.projet.citronix.dto.farm.emdb.EmdbFarmResponseDTO;


public record FieldResponseDTO(

        Long id,

        String name,

        Double area,

        EmdbFarmResponseDTO farm
) {
}

