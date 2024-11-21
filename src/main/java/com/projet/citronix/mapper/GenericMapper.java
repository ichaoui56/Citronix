package com.projet.citronix.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

import javax.swing.text.html.parser.Entity;

@MapperConfig(componentModel = "spring",   unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface GenericMapper<ENTITY, REQUESTDTO, RESPONSEDTO> {
    ENTITY toEntity(REQUESTDTO requestdto);
    RESPONSEDTO toDTO(ENTITY entity);
}
