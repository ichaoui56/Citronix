package com.projet.citronix.mapper;

import com.projet.citronix.dto.field.FieldRequestDTO;
import com.projet.citronix.dto.field.FieldResponseDTO;
import com.projet.citronix.model.Field;
import org.mapstruct.Mapper;


@Mapper(config = GenericMapper.class)
public interface FieldMapper extends GenericMapper<Field, FieldRequestDTO, FieldResponseDTO>{
}
