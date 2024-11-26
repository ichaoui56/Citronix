package com.projet.citronix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Quantity cannot be null")
    @DecimalMin(value = "0.1", inclusive = true, message = "Quantity must be greater than or equal to 0.1")
    private Double quantity;

    @ManyToOne
    @JoinColumn(name = "tree_id")
    @NotNull(message = "Tree must not be null")
    private Tree tree;

    @ManyToOne
    @JoinColumn(name = "harvest_id")
    @NotNull(message = "Harvest must not be null")
    private Harvest harvest;

}
