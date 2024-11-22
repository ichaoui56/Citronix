package com.projet.citronix.controller;

import com.projet.citronix.dto.harvest.HarvestRequestDTO;
import com.projet.citronix.dto.harvest.HarvestResponseDTO;
import com.projet.citronix.dto.harvest.HarvestUpdateDTO;
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

    // Create Harvest
    @PostMapping
    public ResponseEntity<HarvestResponseDTO> createHarvest(@RequestBody HarvestRequestDTO harvestRequestDTO) {
        HarvestResponseDTO response = harvestService.addHarvest(harvestRequestDTO);
        return ResponseEntity.ok(response);
    }

    // Get Harvest by ID
    @GetMapping("/{id}")
    public ResponseEntity<HarvestResponseDTO> getHarvestById(@PathVariable Long id) {
        HarvestResponseDTO response = harvestService.getHarvestById(id);
        return ResponseEntity.ok(response);
    }

    // Get All Harvests
    @GetMapping
    public ResponseEntity<List<HarvestResponseDTO>> getAllHarvests() {
        List<HarvestResponseDTO> response = harvestService.getAllHarvests();
        return ResponseEntity.ok(response);
    }

    // Update Harvest
    @PutMapping("/{id}")
    public ResponseEntity<HarvestResponseDTO> updateHarvest(@PathVariable Long id, @RequestBody HarvestUpdateDTO harvestUpdateDTO) {
        HarvestResponseDTO response = harvestService.updateHarvest(id, harvestUpdateDTO);
        return ResponseEntity.ok(response);
    }

    // Delete Harvest
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHarvest(@PathVariable Long id) {
        harvestService.deleteHarvest(id);
        return ResponseEntity.noContent().build();
    }
}
