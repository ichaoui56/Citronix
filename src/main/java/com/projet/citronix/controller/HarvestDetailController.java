package com.projet.citronix.controller;

import com.projet.citronix.dto.harvestDetail.HarvestDetailRequestDTO;
import com.projet.citronix.dto.harvestDetail.HarvestDetailResponseDTO;
import com.projet.citronix.dto.harvestDetail.HarvestDetailUpdateDTO;
import com.projet.citronix.mapper.HarvestDetailMapper;
import com.projet.citronix.model.HarvestDetail;
import com.projet.citronix.service.HarvestDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/harvest-details")
@RequiredArgsConstructor
public class HarvestDetailController {

    private final HarvestDetailService harvestDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<HarvestDetailResponseDTO> getHarvestDetailById(@PathVariable Long id) {
        HarvestDetailResponseDTO responseDTO = harvestDetailService.getHarvestDetailById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<HarvestDetailResponseDTO>> getAllHarvestDetails() {
        List<HarvestDetailResponseDTO> responseDTOs = harvestDetailService.getAllHarvestDetails();
        return ResponseEntity.ok(responseDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HarvestDetailResponseDTO> updateHarvestDetail(@PathVariable Long id, @RequestBody HarvestDetailUpdateDTO updateDTO) {
        HarvestDetailResponseDTO responseDTO = harvestDetailService.updateHarvestDetail(id, updateDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHarvestDetail(@PathVariable Long id) {
        harvestDetailService.deleteHarvestDetail(id);
        return ResponseEntity.noContent().build();
    }
}
