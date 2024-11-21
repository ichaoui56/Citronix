package com.projet.citronix.service;

import com.projet.citronix.dto.field.FieldRequestDTO;
import com.projet.citronix.dto.field.FieldResponseDTO;

import java.util.List;

public interface FieldService {
    FieldResponseDTO createField(FieldRequestDTO fieldRequestDTO);

    List<FieldResponseDTO> getAllFields();

    FieldResponseDTO getFieldById(Long id);

    boolean deleteField(Long id);

    List<FieldResponseDTO> findAll();

    FieldResponseDTO updateField(Long id, FieldRequestDTO fieldRequestDTO);
}
