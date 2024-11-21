package com.projet.citronix.model;

import jakarta.persistence.*;
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
    private Long id ;
    private String name;
    private String location;
    private Double size;
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

