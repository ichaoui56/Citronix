package com.projet.citronix.mapper;

import com.projet.citronix.dto.farm.FarmRequestDTO;
import com.projet.citronix.dto.farm.FarmResponseDTO;
import com.projet.citronix.model.Farm;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FarmMapper {
    Farm toFarm(FarmRequestDTO farmRequestDTO);
    FarmResponseDTO toFarmResponseDTO(Farm farm);
    void updateFarmFromRequestDTO(FarmRequestDTO farmRequestDTO, @MappingTarget Farm farm);
}
