package com.projet.citronix.service;

import com.projet.citronix.dto.harvest.HarvestRequestDTO;
import com.projet.citronix.dto.harvestDetail.HarvestDetailResponseDTO;
import com.projet.citronix.dto.harvestDetail.HarvestDetailUpdateDTO;
import com.projet.citronix.model.Harvest;
import com.projet.citronix.model.HarvestDetail;
import com.projet.citronix.model.Tree;

import java.util.List;

public interface HarvestDetailService {
    HarvestDetailResponseDTO getHarvestDetailById(Long id);

    List<HarvestDetailResponseDTO> getAllHarvestDetails();

    HarvestDetailResponseDTO updateHarvestDetail(Long id, HarvestDetailUpdateDTO updateDTO);

    void deleteHarvestDetail(Long id);
}
