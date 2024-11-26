package com.projet.citronix.controller;

import com.projet.citronix.dto.field.FieldResponseDTO;
import com.projet.citronix.dto.harvest.HarvestRequestDTO;
import com.projet.citronix.dto.harvest.HarvestResponseDTO;
import com.projet.citronix.model.Field;
import com.projet.citronix.model.Harvest;
import com.projet.citronix.service.HarvestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/harvests")
@AllArgsConstructor
public class HarvestController {

    private final HarvestService harvestService;

    @PostMapping
    public ResponseEntity<HarvestResponseDTO> createHarvest(@RequestBody HarvestRequestDTO harvestRequestDTO) {
        HarvestResponseDTO response = harvestService.addHarvest(harvestRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HarvestResponseDTO> getHarvestById(@PathVariable Long id) {
        HarvestResponseDTO response = harvestService.getHarvestById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<HarvestResponseDTO>> getAllHarvests() {
        List<HarvestResponseDTO> response = harvestService.getAllHarvests();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HarvestResponseDTO> updateHarvest(@PathVariable Long id, @RequestBody HarvestRequestDTO harvestUpdateDTO) {
        HarvestResponseDTO response = harvestService.updateHarvest(id, harvestUpdateDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHarvest(@PathVariable Long id) {
        harvestService.deleteHarvest(id);
        return ResponseEntity.noContent().build();
    }
}
