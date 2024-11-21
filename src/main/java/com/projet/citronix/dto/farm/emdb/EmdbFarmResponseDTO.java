package com.projet.citronix.dto.farm.emdb;

import java.time.LocalDate;


public record EmdbFarmResponseDTO(
     Long id,
     String name,
     String location,
     Double size,
     LocalDate createdDate
) {}