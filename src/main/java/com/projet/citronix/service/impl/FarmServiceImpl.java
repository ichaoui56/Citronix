package com.projet.citronix.service.impl;

import com.projet.citronix.dto.farm.FarmRequestDTO;
import com.projet.citronix.dto.farm.FarmResponseDTO;
import com.projet.citronix.mapper.FarmMapper;
import com.projet.citronix.model.Farm;
import com.projet.citronix.repository.FarmRepository;
import com.projet.citronix.service.FarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;

    @Override
    public FarmResponseDTO addFarm(FarmRequestDTO farmRequestDTO) {
        System.out.println("FarmRequestDTO: " + farmRequestDTO); // Vérifiez les données entrantes
        Farm farm = farmMapper.toFarm(farmRequestDTO);
        System.out.println("Mapped Farm: " + farm); // Vérifiez la conversion DTO -> Entity
        farm = farmRepository.save(farm);
        System.out.println("Saved Farm: " + farm); // Vérifiez les données enregistrées
        return farmMapper.toFarmResponseDTO(farm);
    }


    @Override
    public FarmResponseDTO updateFarm(Long id, FarmRequestDTO farmRequestDTO) {
        Optional<Farm> existingFarmOpt = farmRepository.findById(id);
        if (existingFarmOpt.isPresent()) {
            Farm existingFarm = existingFarmOpt.get();

            farmMapper.updateFarmFromRequestDTO(farmRequestDTO, existingFarm);
            existingFarm = farmRepository.save(existingFarm);
            return farmMapper.toFarmResponseDTO(existingFarm);
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
                .map(farmMapper::toFarmResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FarmResponseDTO getFarmById(Long id) {
        Optional<Farm> farmOpt = farmRepository.findById(id);
        if (farmOpt.isPresent()) {
            return farmMapper.toFarmResponseDTO(farmOpt.get());
        } else {
            throw new RuntimeException("Farm not found");
        }
    }
}
