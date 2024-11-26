package com.projet.citronix.service;

import com.projet.citronix.dto.field.FieldResponseDTO;
import com.projet.citronix.dto.harvest.HarvestRequestDTO;
import com.projet.citronix.dto.harvest.HarvestResponseDTO;
import com.projet.citronix.model.Field;

import java.util.List;

public interface HarvestService {
    HarvestResponseDTO addHarvest(HarvestRequestDTO harvestRequestDTO);
    HarvestResponseDTO getHarvestById(Long id);
    List<HarvestResponseDTO> getAllHarvests();
    HarvestResponseDTO updateHarvest(Long id, HarvestRequestDTO harvestUpdateDTO);
    void deleteHarvest(Long id);
}

