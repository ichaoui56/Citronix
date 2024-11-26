package com.projet.citronix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Plantation date cannot be null")
    @PastOrPresent(message = "Plantation date must be in the past or present")
    private LocalDate plantationDate;

    @ManyToOne
    @JoinColumn(name = "field_id")
    @NotNull(message = "Field must not be null")
    private Field field;

    @OneToMany(mappedBy = "tree", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HarvestDetail> harvestDetails;

    @Transient
    private int age;

    @Transient
    private double productivity;


    public int getAge() {
        return plantationDate != null ? (int) ChronoUnit.YEARS.between(plantationDate, LocalDate.now()) : 0;
    }

    public double getProductivity() {
        int age = getAge();
        if (age < 3) return 2;
        if (age <= 10) return 12;
        if (age <= 20) return 20;
        return 0;
    }

}
