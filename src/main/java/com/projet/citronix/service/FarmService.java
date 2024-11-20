package com.projet.citronix.service;

import com.projet.citronix.dto.farm.FarmRequestDTO;
import com.projet.citronix.dto.farm.FarmResponseDTO;
import com.projet.citronix.dto.farm.FarmSearchDTO;

import java.util.List;

public interface FarmService {

    FarmResponseDTO addFarm(FarmRequestDTO farmRequestDTO);

    FarmResponseDTO updateFarm(Long id, FarmRequestDTO farmRequestDTO);

    boolean removeFarm(Long id);

    List<FarmResponseDTO> getAllFarms();

    FarmResponseDTO getFarmById(Long id);

    List<FarmResponseDTO> getAllFarmsByNameAndLocalisation(FarmSearchDTO searchFermeDTO);
}
