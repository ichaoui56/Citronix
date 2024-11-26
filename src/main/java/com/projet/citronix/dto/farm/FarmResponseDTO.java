package com.projet.citronix.dto.farm;

import com.projet.citronix.dto.farm.emdb.EmdbFarmResponseDTO;
import com.projet.citronix.dto.field.emdb.EmdbFieldResponseDTO;

import java.time.LocalDate;
import java.util.List;

public record FarmResponseDTO(
        Long id,
        String name,
        String location,
        Double size,
        LocalDate createdDate,
        List<EmdbFieldResponseDTO> fields
) {
}
