package com.projet.citronix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Date must be in the past or present")
    private LocalDate date;

    @NotNull(message = "Unit price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
    private Double unitPrice;

    @NotNull(message = "Quantity cannot be null")
    @DecimalMin(value = "0.1", inclusive = true, message = "Quantity must be at least 0.1")
    private Double quantity;

    @NotBlank(message = "Client name cannot be blank")
    @Size(min = 3, max = 255, message = "Client name must be between 3 and 255 characters")
    private String client;

    @ManyToOne
    @JoinColumn(name = "harvest_id")
    @NotNull(message = "Harvest must not be null")
    private Harvest harvest;

    @Transient
    public double getRevenue(){
        return quantity * unitPrice;
    }

}
