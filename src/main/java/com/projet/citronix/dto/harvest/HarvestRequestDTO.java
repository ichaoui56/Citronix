package com.projet.citronix.dto.harvest;

import com.projet.citronix.dto.harvestDetail.HarvestDetailRequestDTO;

import java.time.LocalDate;
import java.util.List;

public record HarvestRequestDTO(
        Long fieldId,
        LocalDate date,
        List<HarvestDetailRequestDTO> harvestDetails
) {
}
