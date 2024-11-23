package com.projet.citronix.mapper;

import com.projet.citronix.dto.sale.SaleRequestDTO;
import com.projet.citronix.dto.sale.SaleResponseDTO;
import com.projet.citronix.model.Sale;
import org.mapstruct.Mapper;

@Mapper(config = GenericMapper.class)
public interface SaleMapper extends GenericMapper<Sale , SaleRequestDTO, SaleResponseDTO> {
}
