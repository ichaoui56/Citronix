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
    private List<Field> fields;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Double getSize() {
        return size;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public List<Field> getFields() {
        return fields;
    }

    public Farm setId(Long id) {
        this.id = id;
        return this;
    }

    public Farm setName(String name) {
        this.name = name;
        return this;
    }

    public Farm setLocation(String location) {
        this.location = location;
        return this;
    }

    public Farm setSize(Double size) {
        this.size = size;
        return this;
    }

    public Farm setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Farm setFields(List<Field> fields) {
        this.fields = fields;
        return this;
    }
}
