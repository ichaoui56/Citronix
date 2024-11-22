package com.projet.citronix.service;

import com.projet.citronix.dto.harvest.HarvestRequestDTO;
import com.projet.citronix.dto.harvest.HarvestResponseDTO;
import com.projet.citronix.dto.harvest.HarvestUpdateDTO;

import java.util.List;

public interface HarvestService {
    HarvestResponseDTO addHarvest(HarvestRequestDTO harvestRequestDTO);
    HarvestResponseDTO getHarvestById(Long id);
    List<HarvestResponseDTO> getAllHarvests();
    HarvestResponseDTO updateHarvest(Long id, HarvestUpdateDTO harvestUpdateDTO);
    void deleteHarvest(Long id);
}

