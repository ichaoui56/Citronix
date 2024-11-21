package com.projet.citronix.service;

import com.projet.citronix.dto.Tree.TreeRequestDTO;
import com.projet.citronix.dto.Tree.TreeResponseDTO;

import java.util.List;

public interface TreeService {
    TreeResponseDTO addTree(TreeRequestDTO treeRequestDTO);

    TreeResponseDTO findById(Long id);

    List<TreeResponseDTO> findAll();

    TreeResponseDTO updateTree(Long treeId, TreeRequestDTO treeRequestDTO);

    void deleteTree(Long treeId);
}
