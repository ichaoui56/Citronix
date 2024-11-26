package com.projet.citronix.controller;

import com.projet.citronix.dto.farm.FarmRequestDTO;
import com.projet.citronix.dto.farm.FarmResponseDTO;
import com.projet.citronix.dto.farm.FarmSearchDTO;

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
    public ResponseEntity<FarmResponseDTO> getFarmById(@PathVariable("farmId") Long id) {
        FarmResponseDTO response = farmService.getFarmById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @DeleteMapping("/{farmId}")
    public ResponseEntity<String> deleteFarmById(@PathVariable("farmId")  Long id) {
        boolean isDeleted = farmService.removeFarm(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Farm deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Farm not found");
    }

    @PutMapping("/{farmId}")
    public ResponseEntity<FarmResponseDTO> updateFarm(@PathVariable("farmId") Long farmId, @RequestBody @Valid FarmRequestDTO farmRequestDTO) {
        System.out.println(farmRequestDTO);
        FarmResponseDTO response = farmService.updateFarm(farmId, farmRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FarmResponseDTO>> searchFarms(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "size", required = false) Double size,
            @RequestParam(value = "createdDateAfter", required = false) String createdDateAfter) {

        FarmSearchDTO searchDTO = new FarmSearchDTO(name, location, size, createdDateAfter);
        List<FarmResponseDTO> response = farmService.getAllFarmsByNameAndLocalisation(searchDTO);
        return ResponseEntity.ok(response);
    }



}
