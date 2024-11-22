package com.projet.citronix.mapper;

import com.projet.citronix.dto.harvest.HarvestRequestDTO;
import com.projet.citronix.dto.harvest.HarvestResponseDTO;
import com.projet.citronix.model.Harvest;
import org.mapstruct.Mapper;

@Mapper(config = GenericMapper.class)
public interface HarvestMapper extends GenericMapper<Harvest, HarvestRequestDTO, HarvestResponseDTO>{
}
