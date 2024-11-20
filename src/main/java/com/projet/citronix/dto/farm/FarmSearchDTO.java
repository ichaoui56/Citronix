package com.projet.citronix.dto.farm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmSearchDTO {
    private String name;
    private String location;
    private Double size;
    private String createdDateAfter;
}
