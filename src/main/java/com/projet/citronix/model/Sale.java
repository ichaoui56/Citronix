package com.projet.citronix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Date must be in the past or present")
    private LocalDate date;

    @NotNull(message = "Unit price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
    private Double unitPrice;

    @NotNull(message = "Quantity cannot be null")
    @DecimalMin(value = "0.1", inclusive = true, message = "Quantity must be at least 0.1")
    private Double quantity;

    @NotNull(message = "Revenue cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Revenue must be at least 0")
    private Double revenue; // Ensure to calculate this in your service or setter

    @NotBlank(message = "Client name cannot be blank")
    @Size(min = 3, max = 255, message = "Client name must be between 3 and 255 characters")
    private String client;

    @ManyToOne
    @JoinColumn(name = "harvest_id")
    @NotNull(message = "Harvest must not be null")
    private Harvest harvest;

    public Long getId() {
        return id;
    }

    public Sale setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Sale setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Sale setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Sale setQuantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public Double getRevenue() {
        return revenue;
    }

    public Sale setRevenue(Double revenue) {
        this.revenue = revenue;
        return this;
    }

    public String getClient() {
        return client;
    }

    public Sale setClient(String client) {
        this.client = client;
        return this;
    }

    public Harvest getHarvest() {
        return harvest;
    }

    public Sale setHarvest(Harvest harvest) {
        this.harvest = harvest;
        return this;
    }
}
