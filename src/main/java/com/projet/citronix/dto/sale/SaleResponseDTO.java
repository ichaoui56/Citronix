package com.projet.citronix.dto.sale;

import com.projet.citronix.dto.harvest.emdb.EmdbHarvestResponseDTO;
import com.projet.citronix.model.Harvest;

import java.time.LocalDate;

public record SaleResponseDTO(
    Long id,
    LocalDate date,
    Double unitPrice,
    Double quantity,
    String client,
    Double revenue,
    EmdbHarvestResponseDTO harvest
) {
}
