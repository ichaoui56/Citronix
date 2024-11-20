package com.projet.citronix.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private Double unitPrice;
    private Double quantity;
    private Double revenue; // calculated as quantity * unitPrice
    private String client;

    @ManyToOne
    @JoinColumn(name = "harvest_id")
    private Harvest harvest;
}
