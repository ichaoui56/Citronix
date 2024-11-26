package com.projet.citronix.service.impl;

import com.projet.citronix.dto.farm.FarmRequestDTO;
import com.projet.citronix.dto.farm.FarmResponseDTO;
import com.projet.citronix.dto.farm.FarmSearchDTO;
import com.projet.citronix.exception.EntityNotFoundException;
import com.projet.citronix.exception.SearchNotFoundException;
import com.projet.citronix.mapper.FarmMapper;
import com.projet.citronix.model.Farm;
import com.projet.citronix.repository.FarmRepository;
import com.projet.citronix.repository.criteriaBuilder.IFarmCriteria;
import com.projet.citronix.service.FarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;
    private final IFarmCriteria farmCriteria;

    @Override
    @Transactional
    public FarmResponseDTO addFarm(FarmRequestDTO farmRequestDTO) {
        Farm farm = farmMapper.toEntity(farmRequestDTO);
        validateFarmSize(farm.getSize());
        Farm savedFarm = farmRepository.save(farm);
        return farmMapper.toDTO(savedFarm);
    }

    @Override
    @Transactional
    public FarmResponseDTO updateFarm(Long id, FarmRequestDTO farmRequestDTO) {
        Farm existingFarm = findTheFarmById(id);
        validateFarmSize(farmRequestDTO.size());

        farmMapper.updateEntityFromDTO(farmRequestDTO, existingFarm);
        Farm updatedFarm = farmRepository.save(existingFarm);
        return farmMapper.toDTO(updatedFarm);
    }

    @Override
    @Transactional
    public boolean removeFarm(Long id) {
        Farm farm = findTheFarmById(id);
        farmRepository.delete(farm);
        return true;
    }

    @Override
    public List<FarmResponseDTO> getAllFarms() {
        return farmRepository.findAll().stream()
                .map(farmMapper::toDTO)
                .toList();
    }


    @Override
    public FarmResponseDTO getFarmById(Long id) {
        Farm farm = findTheFarmById(id);
        return farmMapper.toDTO(farm);
    }

    @Override
    public List<FarmResponseDTO> getAllFarmsByNameAndLocalisation(FarmSearchDTO searchDTO) {
        Map<String, Object> filters = buildFilterMap(searchDTO);

        List<Farm> farms = farmCriteria.findFarmsByCriteria(filters);
        if (farms.isEmpty()) {
            throw new SearchNotFoundException("No farms found matching the criteria.");
        }

        return farms.stream()
                .map(farmMapper::toDTO)
                .toList();
    }

    /**
     * Finds the farm by ID and throws a custom exception if not found.
     */
    private Farm findTheFarmById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Field", id));
    }

    /**
     * Validates that the farm size meets the minimum requirement.
     */
    private void validateFarmSize(double size) {
        if (size < 1000) {
            throw new IllegalArgumentException("Farm area size must not be less than 1000.");
        }
    }

    /**
     * Builds a filter map from the FarmSearchDTO.
     */
    private Map<String, Object> buildFilterMap(FarmSearchDTO searchDTO) {
        Map<String, Object> filters = new HashMap<>();
        if (searchDTO.name() != null) {
            filters.put("name", searchDTO.name());
        }
        if (searchDTO.location() != null) {
            filters.put("location", searchDTO.location());
        }
        if (searchDTO.size() != null) {
            filters.put("size", searchDTO.size());
        }
        if (searchDTO.createdDateAfter() != null) {
            filters.put("createdDateAfter", searchDTO.createdDateAfter());
        }
        return filters;
    }
}
