package com.projet.citronix.dto.harvestDetail;

import jakarta.validation.constraints.*;

public record HarvestDetailRequestDTO(
        @NotNull(message = "Harvest detail ID is required")
        @Positive(message = "Harvest detail ID must be a positive value")
        Long id,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be a positive value")
        Double quantity
) { }
