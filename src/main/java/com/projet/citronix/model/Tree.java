package com.projet.citronix.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate plantationDate;

    @Transient
    private int age;

    @Transient
    private int productivity;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @OneToMany(mappedBy = "tree", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HarvestDetail> harvestDetails;

    public int getAge() {
        return calculateAge();
    }

    public int getProductivity(){
        return calculateProductivity();
    }

    private int calculateAge() {
        LocalDate currentDate = LocalDate.now();
        assert plantationDate != null;

        Period period = Period.between(plantationDate, currentDate);
        int years = period.getYears();
        int months = period.getMonths();
        return years + (months / 12);
    }

    private int calculateProductivity() {
        int age = getAge();

        if (age < 3) {
            return 2;
        } else if (age <= 10) {
            return 12;
        } else if (age <= 20) {
            return 20;
        } else {
            return 0;
        }
    }

    public Long getId() {
        return id;
    }

    public Tree setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getPlantationDate() {
        return plantationDate;
    }

    public Tree setPlantationDate(LocalDate plantationDate) {
        this.plantationDate = plantationDate;
        return this;
    }

    public Field getField() {
        return field;
    }

    public Tree setField(Field field) {
        this.field = field;
        return this;
    }

    public List<HarvestDetail> getHarvestDetails() {
        return harvestDetails;
    }

    public Tree setHarvestDetails(List<HarvestDetail> harvestDetails) {
        this.harvestDetails = harvestDetails;
        return this;
    }
}