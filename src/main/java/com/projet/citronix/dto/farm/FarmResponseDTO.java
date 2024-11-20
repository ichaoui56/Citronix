package com.projet.citronix.dto.farm;

import lombok.Data;

@Data
public class FarmResponseDTO {
    private Long id;
    private String name;
    private String location;
    private Double size;
    private String createdDate;
}
