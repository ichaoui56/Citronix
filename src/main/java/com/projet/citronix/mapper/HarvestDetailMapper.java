package com.projet.citronix.mapper;

import com.projet.citronix.dto.harvestDetail.HarvestDetailRequestDTO;
import com.projet.citronix.dto.harvestDetail.HarvestDetailResponseDTO;
import com.projet.citronix.model.HarvestDetail;
import org.mapstruct.Mapper;

@Mapper(config = GenericMapper.class)
public interface HarvestDetailMapper extends GenericMapper<HarvestDetail, HarvestDetailRequestDTO, HarvestDetailResponseDTO>{
}
