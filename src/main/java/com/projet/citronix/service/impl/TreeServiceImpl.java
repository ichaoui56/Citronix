package com.projet.citronix.service.impl;

import com.projet.citronix.dto.Tree.TreeRequestDTO;
import com.projet.citronix.dto.Tree.TreeResponseDTO;
import com.projet.citronix.mapper.TreeMapper;
import com.projet.citronix.model.Field;
import com.projet.citronix.model.Tree;
import com.projet.citronix.repository.FieldRepository;
import com.projet.citronix.repository.TreeRepository;
import com.projet.citronix.service.TreeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public TreeResponseDTO addTree(TreeRequestDTO treeRequestDTO) {
        validatePlantationDate(treeRequestDTO.plantationDate().getMonthValue());

        Tree tree = treeMapper.toEntity(treeRequestDTO);
        Field field = fieldRepository.findById(treeRequestDTO.field_id())
                .orElseThrow(() -> new EntityNotFoundException("Field does not exist."));

        validateTreeCount(field);

        tree.setField(field);
        Tree savedTree = treeRepository.save(tree);
        return treeMapper.toDTO(savedTree);
    }

    @Override
    public TreeResponseDTO findById(Long id) {
        Tree tree = treeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tree not found."));
        return treeMapper.toDTO(tree);
    }

    @Override
    public List<TreeResponseDTO> findAll() {
        List<Tree> trees = treeRepository.findAll();
        return trees.stream()
                .map(treeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TreeResponseDTO updateTree(Long treeId, TreeRequestDTO treeRequestDTO) {
        Tree tree = treeRepository.findById(treeId)
                .orElseThrow(() -> new EntityNotFoundException("Tree not found."));

        validatePlantationDate(treeRequestDTO.plantationDate().getMonthValue());

        Field field = fieldRepository.findById(treeRequestDTO.field_id())
                .orElseThrow(() -> new EntityNotFoundException("Field does not exist."));

        validateTreeCount(field);

        tree.setPlantationDate(treeRequestDTO.plantationDate());
        tree.setField(field);

        Tree updatedTree = treeRepository.save(tree);
        return treeMapper.toDTO(updatedTree);
    }

    @Override
    public void deleteTree(Long treeId) {
        Tree tree = treeRepository.findById(treeId)
                .orElseThrow(() -> new EntityNotFoundException("Tree not found."));
        treeRepository.delete(tree);
    }

    private void validatePlantationDate(int month) {
        if (month >= Month.MARCH.getValue() && month <= Month.MAY.getValue()) {
            throw new IllegalArgumentException("Trees cannot be planted between March and May.");
        }
    }

    private void validateTreeCount(Field field) {
        double allowableTreeCount = (field.getArea() * 10) / 1000;
        if (field.getTrees().size() >= allowableTreeCount) {
            throw new IllegalStateException("You have exceeded the limit of allowable trees for this field.");
        }
    }
}
