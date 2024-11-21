package com.projet.citronix.dto.farm;

import com.projet.citronix.dto.farm.emdb.EmdbFarmResponseDTO;
import com.projet.citronix.dto.field.emdb.EmdbFieldResponseDTO;

import java.util.List;

public record FarmResponseDTO(
        Long id,
        String name,
        String location,
        Double size,
        String createdDate,
        List<EmdbFieldResponseDTO> fieldList
) {

}
