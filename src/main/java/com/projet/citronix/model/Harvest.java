package com.projet.citronix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Harvest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Season cannot be blank")
    @Pattern(regexp = "^(SPRING|SUMMER|AUTUMN|WINTER)$", message = "Season must be one of: Spring, Summer, Autumn, Winter")
    private String season;

    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Date must be in the past or present")
    private LocalDate date;

    @NotNull(message = "Total quantity cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total quantity must be greater than 0")
    private Double totalQuantity;

    @ManyToOne
    @JoinColumn(name = "field_id")
    @NotNull(message = "Field must not be null")
    private Field field;

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HarvestDetail> harvestDetails;

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sale> sales;

    public Long getId() {
        return id;
    }

    public Harvest setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSeason() {
        return season;
    }

    public Harvest setSeason(String season) {
        this.season = season;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Harvest setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Double getTotalQuantity() {
        return totalQuantity;
    }

    public Harvest setTotalQuantity(Double totalQuantity) {
        this.totalQuantity = totalQuantity;
        return this;
    }

    public Field getField() {
        return field;
    }

    public Harvest setField(Field field) {
        this.field = field;
        return this;
    }

    public List<HarvestDetail> getHarvestDetails() {
        return harvestDetails;
    }

    public Harvest setHarvestDetails(List<HarvestDetail> harvestDetails) {
        this.harvestDetails = harvestDetails;
        return this;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public Harvest setSales(List<Sale> sales) {
        this.sales = sales;
        return this;
    }
}
