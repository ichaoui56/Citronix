package com.projet.citronix.mapper;

import com.projet.citronix.dto.Tree.TreeRequestDTO;
import com.projet.citronix.dto.Tree.TreeResponseDTO;
import com.projet.citronix.model.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@Mapper(config = GenericMapper.class)
public interface TreeMapper extends GenericMapper<Tree, TreeRequestDTO, TreeResponseDTO>{
}
