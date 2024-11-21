package com.projet.citronix.dto.farm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record FarmSearchDTO(
     String name,
     String location,
     Double size,
     String createdDateAfter
)
{}

