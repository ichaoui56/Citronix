package com.projet.citronix.service.impl;

import com.projet.citronix.dto.field.FieldRequestDTO;
import com.projet.citronix.dto.field.FieldResponseDTO;
import com.projet.citronix.exception.EntityNotFoundException;
import com.projet.citronix.mapper.FieldMapper;
import com.projet.citronix.model.Farm;
import com.projet.citronix.model.Field;
import com.projet.citronix.repository.FarmRepository;
import com.projet.citronix.repository.FieldRepository;
import com.projet.citronix.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;
    private final FieldMapper fieldMapper;

    @Override
    @Transactional
    public FieldResponseDTO createField(FieldRequestDTO fieldRequestDTO) {
        Farm farm = getFarmById(fieldRequestDTO.farm_id());

        validateFieldConstraints(fieldRequestDTO, farm);

        Field field = fieldMapper.toEntity(fieldRequestDTO);
        field.setFarm(farm);

        Field savedField = fieldRepository.save(field);

        return fieldMapper.toDTO(savedField);
    }


    @Override
    public List<FieldResponseDTO> getAllFields() {
        return fieldRepository.findAll().stream()
                .map(fieldMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FieldResponseDTO getFieldById(Long id) {
        Field field = getFieldEntityById(id);
        return fieldMapper.toDTO(field);
    }

    @Override
    public boolean deleteField(Long id) {
        if (fieldRepository.existsById(id)) {
            fieldRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public FieldResponseDTO updateField(Long id, FieldRequestDTO fieldRequestDTO) {
        Field existingField = getFieldEntityById(id);
        Farm farm = getFarmById(fieldRequestDTO.farm_id());

        validateFieldConstraints(fieldRequestDTO, farm);

        fieldMapper.updateEntityFromDTO(fieldRequestDTO, existingField);

        Field updatedField = fieldRepository.save(existingField);
        return fieldMapper.toDTO(updatedField);
    }

    /**
     * Retrieves a Farm entity by its ID.
     */
    private Farm getFarmById(Long farmId) {
        return farmRepository.findById(farmId)
                .orElseThrow(() -> new EntityNotFoundException("Field", farmId));
    }

    /**
     * Retrieves a Field entity by its ID.
     */
    private Field getFieldEntityById(Long fieldId) {
        return fieldRepository.findById(fieldId)
                .orElseThrow(() -> new EntityNotFoundException("Field", fieldId));
    }

    /**
     * Validates constraints for creating or updating a Field in a Farm.
     */
    private void validateFieldConstraints(FieldRequestDTO fieldRequestDTO, Farm farm) {
        double allowableSuperficial = farm.getSize() / 2;
        double totalExistingSuperficial = farm.getFields().stream()
                .mapToDouble(Field::getArea)
                .sum();

        double currentSize = farm.getSize() - totalExistingSuperficial;

        if (farm.getFields().size() >= 10) {
            System.out.println("A farm cannot have more than 10 fields.");
        }

        if (fieldRequestDTO.area() > allowableSuperficial) {
            System.out.println(
                    "Field area must not exceed " + allowableSuperficial + " (50% of the farm size).");
        }

        if (fieldRequestDTO.area() > currentSize) {
            double availableArea = farm.getSize() - totalExistingSuperficial;
            System.out.println("Exceeded limit. Only " + availableArea + " is available.");
        }
    }
}
