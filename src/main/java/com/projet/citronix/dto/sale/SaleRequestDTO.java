package com.projet.citronix.dto.sale;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record SaleRequestDTO(
        @NotNull(message = "Sale date is required")
        @PastOrPresent(message = "Sale date cannot be in the future")
        LocalDate date,

        @NotNull(message = "Unit price is required")
        @Positive(message = "Unit price must be a positive value")
        Double unitPrice,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be a positive value")
        Double quantity,

        @NotBlank(message = "Client name is required")
        @Size(max = 100, message = "Client name must not exceed 100 characters")
        String client,

        @NotNull(message = "Harvest ID is required")
        @Positive(message = "Harvest ID must be a positive value")
        Long harvestId
) {
}
