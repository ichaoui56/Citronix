package com.projet.citronix.controller;

import com.projet.citronix.dto.farm.FarmResponseDTO;
import com.projet.citronix.dto.sale.SaleRequestDTO;
import com.projet.citronix.dto.sale.SaleResponseDTO;
import com.projet.citronix.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleControlller {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleResponseDTO> createSale(@Valid @RequestBody SaleRequestDTO requestDTO) {
        SaleResponseDTO responseDTO = saleService.addSale(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> removeSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> updateSale(@PathVariable Long id, @RequestBody SaleRequestDTO requestDTO) {
        SaleResponseDTO responseDTO = saleService.updateSale(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> getSaleByid(@PathVariable Long id) {
        SaleResponseDTO saleResponseDTO = saleService.findById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(saleResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<SaleResponseDTO>> getAllSales() {
        List<SaleResponseDTO> response = saleService.findAll();
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }
}
