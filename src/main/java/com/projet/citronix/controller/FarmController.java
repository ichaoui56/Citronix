package com.projet.citronix.controller;

import com.projet.citronix.annotation.checkExistance.Exist;
import com.projet.citronix.dto.farm.FarmRequestDTO;
import com.projet.citronix.dto.farm.FarmResponseDTO;
import com.projet.citronix.model.Farm;
import com.projet.citronix.repository.FarmRepository;
import com.projet.citronix.service.FarmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farms")
@RequiredArgsConstructor
public class FarmController {

    private final FarmService farmService;

    @PostMapping
    public ResponseEntity<FarmResponseDTO> createFarm(@RequestBody @Valid FarmRequestDTO farmRequestDTO) {
        FarmResponseDTO response = farmService.addFarm(farmRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<FarmResponseDTO>> getAllFarms() {
        List<FarmResponseDTO> response = farmService.getAllFarms();
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @GetMapping("/{farmId}")
    public ResponseEntity<FarmResponseDTO> getFarmById(@PathVariable("farmId") @Exist(entity = Farm.class, repository = FarmRepository.class) Long id) {
        FarmResponseDTO response = farmService.getFarmById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @DeleteMapping("/{farmId}")
    public ResponseEntity<String> deleteFarmById(@PathVariable("farmId") @Exist(entity = Farm.class, repository = FarmRepository.class) Long id) {
        boolean isDeleted = farmService.removeFarm(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Farm deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Farm not found");
    }

    @PutMapping("/{farmId}")
    public ResponseEntity<FarmResponseDTO> updateFarm(@PathVariable("farmId") Long farmId, @RequestBody @Valid FarmRequestDTO farmRequestDTO) {
        FarmResponseDTO response = farmService.updateFarm(farmId, farmRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
