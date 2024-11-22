package com.projet.citronix.controller;

import com.projet.citronix.dto.Tree.TreeRequestDTO;
import com.projet.citronix.dto.Tree.TreeResponseDTO;
import com.projet.citronix.service.TreeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trees")
@RequiredArgsConstructor
public class TreeController {

    private final TreeService treeService;

    @PostMapping
    public ResponseEntity<TreeResponseDTO> addTree(@Valid @RequestBody TreeRequestDTO treeRequestDTO) {
        TreeResponseDTO response = treeService.addTree(treeRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{treeId}")
    public ResponseEntity<TreeResponseDTO> getTreeById(@PathVariable Long treeId) {
        TreeResponseDTO responseDTO = treeService.findById(treeId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<TreeResponseDTO>> getAllTrees() {
        List<TreeResponseDTO> responseList = treeService.findAll();
        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{treeId}")
    public ResponseEntity<TreeResponseDTO> updateTree(@PathVariable Long treeId, @RequestBody TreeRequestDTO treeRequestDTO) {
        TreeResponseDTO updatedTree = treeService.updateTree(treeId, treeRequestDTO);
        return ResponseEntity.ok(updatedTree);
    }

    @DeleteMapping("/{treeId}")
    public ResponseEntity<Void> deleteTree(@PathVariable Long treeId) {
        treeService.deleteTree(treeId);
        return ResponseEntity.noContent().build();
    }
}
