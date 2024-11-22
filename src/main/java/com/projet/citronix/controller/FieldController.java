package com.projet.citronix.controller;

import com.projet.citronix.dto.field.FieldRequestDTO;
import com.projet.citronix.dto.field.FieldResponseDTO;
import com.projet.citronix.service.FieldService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;

    @PostMapping
    public ResponseEntity<FieldResponseDTO> createField(@Valid  @RequestBody FieldRequestDTO fieldRequestDTO) {
        FieldResponseDTO response = fieldService.createField(fieldRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<FieldResponseDTO>> getAllFields() {
        List<FieldResponseDTO> response = fieldService.getAllFields();
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @GetMapping("/{fieldId}")
    public ResponseEntity<FieldResponseDTO> getFieldById(@PathVariable Long fieldId) {
        FieldResponseDTO response = fieldService.getFieldById(fieldId);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @DeleteMapping("/{fieldId}")
    public ResponseEntity<String> deleteFieldById(@PathVariable Long fieldId) {
        boolean isDeleted = fieldService.deleteField(fieldId);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Field deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Field not found");
    }

        @PutMapping("/{fieldId}")
        public ResponseEntity<FieldResponseDTO> updateField(@PathVariable Long fieldId, @RequestBody FieldRequestDTO fieldRequestDTO) {
            FieldResponseDTO response = fieldService.updateField(fieldId, fieldRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
}
