package com.projet.citronix.dto.harvestDetail;

import com.projet.citronix.dto.Tree.emdb.EmdbTreeResponseDTO;
import com.projet.citronix.dto.harvest.emdb.EmdbHarvestResponseDTO;

public record HarvestDetailResponseDTO(
        Long id,
        EmdbTreeResponseDTO tree,
        EmdbHarvestResponseDTO harvest,
        Double quantity
) { }

