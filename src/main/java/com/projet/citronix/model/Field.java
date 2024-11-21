package com.projet.citronix.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double area;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tree> trees;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Harvest> harvests;

    public Long getId() {
        return id;
    }

    public Field setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Field setName(String name) {
        this.name = name;
        return this;
    }

    public Double getArea() {
        return area;
    }

    public Field setArea(Double area) {
        this.area = area;
        return this;
    }

    public Farm getFarm() {
        return farm;
    }

    public Field setFarm(Farm farm) {
        this.farm = farm;
        return this;
    }

    public List<Tree> getTrees() {
        return trees;
    }

    public Field setTrees(List<Tree> trees) {
        this.trees = trees;
        return this;
    }

    public List<Harvest> getHarvests() {
        return harvests;
    }

    public Field setHarvests(List<Harvest> harvests) {
        this.harvests = harvests;
        return this;
    }
}
