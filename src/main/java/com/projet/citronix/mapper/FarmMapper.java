package com.projet.citronix.mapper;

import com.projet.citronix.dto.farm.FarmRequestDTO;
import com.projet.citronix.dto.farm.FarmResponseDTO;
import com.projet.citronix.model.Farm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GenericMapper.class)
public interface FarmMapper extends GenericMapper<Farm, FarmRequestDTO, FarmResponseDTO>{
}

