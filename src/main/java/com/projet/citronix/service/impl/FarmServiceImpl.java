package com.projet.citronix.service.impl;

import com.projet.citronix.dto.farm.FarmRequestDTO;
import com.projet.citronix.dto.farm.FarmResponseDTO;
import com.projet.citronix.dto.farm.FarmSearchDTO;
import com.projet.citronix.mapper.FarmMapper;
import com.projet.citronix.model.Farm;
import com.projet.citronix.repository.FarmRepository;
import com.projet.citronix.repository.criteriaBuilder.IFarmCriteria;
import com.projet.citronix.service.FarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;
    private final IFarmCriteria fermeCriteria;

    @Override
    public FarmResponseDTO addFarm(FarmRequestDTO farmRequestDTO) {
        System.out.println("service: " + farmRequestDTO.name());
        Farm farm = farmMapper.toEntity(farmRequestDTO);
        System.out.println("mapper: " + farm.getName());
        farm = farmRepository.save(farm);
        System.out.println("response: " + farmMapper.toDTO(farm));

        return farmMapper.toDTO(farm);
    }

    @Override
    public FarmResponseDTO updateFarm(Long id, FarmRequestDTO farmRequestDTO) {
        Optional<Farm> existingFarmOpt = farmRepository.findById(id);
        if (existingFarmOpt.isPresent()) {
            Farm existingFarm = existingFarmOpt.get();

            farmMapper.toEntity(farmRequestDTO);
            existingFarm = farmRepository.save(existingFarm);
            return farmMapper.toDTO(existingFarm);
        } else {
            throw new RuntimeException("Farm not found");
        }
    }

    @Override
    public boolean removeFarm(Long id) {
        Optional<Farm> farmOpt = farmRepository.findById(id);
        if (farmOpt.isPresent()) {
            farmRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<FarmResponseDTO> getAllFarms() {
        List<Farm> farms = farmRepository.findAll();
        return farms.stream()
                .map(farmMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FarmResponseDTO getFarmById(Long id) {
        Optional<Farm> farmOpt = farmRepository.findById(id);
        if (farmOpt.isPresent()) {
            return farmMapper.toDTO(farmOpt.get());
        } else {
            throw new RuntimeException("Farm not found");
        }
    }

    @Override
    public List<FarmResponseDTO> getAllFarmsByNameAndLocalisation(FarmSearchDTO searchFermeDTO) {

        Map<String, Object> filters = new HashMap<>();
        if (searchFermeDTO.name() != null) {
            filters.put("name", searchFermeDTO.name());
        }
        if (searchFermeDTO.location() != null) {
            filters.put("location", searchFermeDTO.location());
        }
        if (searchFermeDTO.size() != null) {
            filters.put("size", searchFermeDTO.size());
        }
        if (searchFermeDTO.createdDateAfter() != null) {
            filters.put("createdDateAfter", searchFermeDTO.createdDateAfter());
        }

        List<Farm> farms = fermeCriteria.findFarmsByCriteria(filters);
        if (farms.isEmpty()) {
            throw new RuntimeException("No farms found matching the criteria");
        }

        return farms.stream().map(farmMapper::toDTO).toList();
    }

}
