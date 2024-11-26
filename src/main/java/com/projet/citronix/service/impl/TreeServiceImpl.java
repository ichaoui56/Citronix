package com.projet.citronix.service.impl;

import com.projet.citronix.dto.Tree.TreeRequestDTO;
import com.projet.citronix.dto.Tree.TreeResponseDTO;
import com.projet.citronix.mapper.TreeMapper;
import com.projet.citronix.model.Field;
import com.projet.citronix.model.Tree;
import com.projet.citronix.model.enums.SeasonType;
import com.projet.citronix.repository.FieldRepository;
import com.projet.citronix.repository.TreeRepository;
import com.projet.citronix.service.TreeService;
import com.projet.citronix.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreeServiceImpl implements TreeService {

    private final TreeRepository treeRepository;
    private final TreeMapper treeMapper;
    private final FieldRepository fieldRepository;

    @Override
    @Transactional
    public TreeResponseDTO addTree(TreeRequestDTO treeRequestDTO) {
        validatePlantationDate(treeRequestDTO.plantationDate().getMonthValue());
        Field field = validateAndRetrieveField(treeRequestDTO.field_id());
        validateTreeCount(field);

        Tree tree = treeMapper.toEntity(treeRequestDTO);
        tree.setField(field);

        Tree savedTree = treeRepository.save(tree);
        return treeMapper.toDTO(savedTree);
    }

    @Override
    public TreeResponseDTO findById(Long id) {
        Tree tree = retrieveTreeById(id);
        return treeMapper.toDTO(tree);
    }

    @Override
    public List<TreeResponseDTO> findAll() {
        return treeRepository.findAll()
                .stream()
                .map(treeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TreeResponseDTO updateTree(Long treeId, TreeRequestDTO treeRequestDTO) {
        validatePlantationDate(treeRequestDTO.plantationDate().getMonthValue());
        Tree tree = retrieveTreeById(treeId);
        Field field = validateAndRetrieveField(treeRequestDTO.field_id());
        validateTreeCount(field);

        treeMapper.updateEntityFromDTO(treeRequestDTO, tree);

        Tree updatedTree = treeRepository.save(tree);
        return treeMapper.toDTO(updatedTree);
    }

    @Override
    @Transactional
    public void deleteTree(Long treeId) {
        Tree tree = retrieveTreeById(treeId);
        treeRepository.delete(tree);
    }

    /**
     * Validates the plantation date to ensure it's within an acceptable range.
     */
    private void validatePlantationDate(int month) {
        if (month >= Month.MARCH.getValue() && month <= Month.MAY.getValue()) {
            throw new IllegalArgumentException("Trees cannot be planted between March and May.");
        }
    }

    /**
     * Validates the tree count in the field to ensure it doesn't exceed the allowable limit.
     */
    private void validateTreeCount(Field field) {
        double allowableTreeCount = (field.getArea() * 10) / 1000;
        if (field.getTrees().size() >= allowableTreeCount) {
            throw new IllegalArgumentException("You have exceeded the limit of allowable trees for this field.");
        }
    }

    /**
     * Retrieves a Field by its ID and ensures it exists.
     */
    private Field validateAndRetrieveField(Long fieldId) {
        return fieldRepository.findById(fieldId)
                .orElseThrow(() -> new EntityNotFoundException("Field" ,fieldId));
    }

    /**
     * Retrieves a Tree by its ID and ensures it exists.
     */
    private Tree retrieveTreeById(Long treeId) {
        return treeRepository.findById(treeId)
                .orElseThrow(() -> new EntityNotFoundException("Tree", treeId));
    }
}
