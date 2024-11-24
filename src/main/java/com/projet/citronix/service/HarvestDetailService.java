package com.projet.citronix.service;

import com.projet.citronix.dto.harvestDetail.HarvestDetailRequestDTO;
import com.projet.citronix.dto.harvestDetail.HarvestDetailResponseDTO;

import java.util.List;

public interface HarvestDetailService {
    HarvestDetailResponseDTO getHarvestDetailById(Long id);

    List<HarvestDetailResponseDTO> getAllHarvestDetails();

    HarvestDetailResponseDTO updateHarvestDetail(Long id, HarvestDetailRequestDTO updateDTO);

    void deleteHarvestDetail(Long id);
}
