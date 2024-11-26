package com.projet.citronix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Farm name cannot be blank")
    @Size(min = 3, max = 50, message = "Farm name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Location cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9\\s,.-]+$", message = "Location can only contain letters, numbers, spaces, commas, periods, and hyphens")
    private String location;

    @NotNull(message = "Farm size cannot be null")
    @DecimalMin(value = "0.1", inclusive = true, message = "Farm size must be greater than or equal to 0.1")
    private Double size;

    @NotNull(message = "Created date cannot be null")
    @PastOrPresent(message = "Created date must be in the past or present")
    private LocalDate createdDate;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Field> fields = new ArrayList<>();

}
