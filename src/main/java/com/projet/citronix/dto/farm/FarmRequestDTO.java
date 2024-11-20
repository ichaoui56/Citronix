package com.projet.citronix.dto.farm;

import lombok.Data;
import java.time.LocalDate;

@Data
public class FarmRequestDTO {
    private String name;
    private String location;
    private Double size;
    private LocalDate createdDate;
}
