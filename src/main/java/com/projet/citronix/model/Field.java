package com.projet.citronix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Field name cannot be blank")
    @Size(min = 3, max = 50, message = "Field name must be between 3 and 50 characters")
    private String name;

    @NotNull(message = "Field area cannot be null")
    @DecimalMin(value = "0.1", inclusive = true, message = "Field area must be greater than or equal to 0.1")
    private Double area;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    @NotNull(message = "Farm must not be null")
    private Farm farm;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tree> trees = new ArrayList<>();;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Harvest> harvests;


}
