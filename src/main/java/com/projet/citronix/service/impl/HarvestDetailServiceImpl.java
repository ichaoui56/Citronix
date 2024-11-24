package com.projet.citronix.service.impl;

import com.projet.citronix.dto.harvestDetail.HarvestDetailRequestDTO;
import com.projet.citronix.dto.harvestDetail.HarvestDetailResponseDTO;
import com.projet.citronix.event.HarvestCreatedEvent;
import com.projet.citronix.mapper.HarvestDetailMapper;
import com.projet.citronix.model.Harvest;
import com.projet.citronix.model.HarvestDetail;
import com.projet.citronix.model.Tree;
import com.projet.citronix.repository.HarvestDetailRepository;
import com.projet.citronix.repository.HarvestRepository;
import com.projet.citronix.service.HarvestDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HarvestDetailServiceImpl implements HarvestDetailService {

    private final HarvestDetailRepository harvestDetailRepository;
    private final HarvestRepository harvestRepository;
    private final HarvestDetailMapper harvestDetailMapper;

    /**
     * Adds HarvestDetails to a Harvest when the HarvestCreatedEvent is triggered.
     */
    @EventListener
    @Transactional
    public List<HarvestDetailResponseDTO> addHarvestDetail(HarvestCreatedEvent event) {
        List<Tree> trees = event.trees();
        Harvest harvest = event.harvest();

        List<HarvestDetail> harvestDetailsList = trees.stream()
                .map(tree -> HarvestDetail.builder()
                        .harvest(harvest)
                        .tree(tree)
                        .quantity(tree.getProductivity())
                        .build())
                .collect(Collectors.toList());

        List<HarvestDetail> allHarvestDetailsList = harvestDetailRepository.saveAll(harvestDetailsList);

        harvest.setHarvestDetails(allHarvestDetailsList);
        harvest.setTotalQuantity(recalculateTotalQuantity(harvest));
        harvestRepository.save(harvest);

        return allHarvestDetailsList.stream()
                .map(harvestDetailMapper::toDTO)
                .toList();
    }

    /**
     * Gets a single HarvestDetail by its ID.
     */
    @Override
    @Transactional(readOnly = true)
    public HarvestDetailResponseDTO getHarvestDetailById(Long id) {
        HarvestDetail harvestDetail = harvestDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("HarvestDetail not found"));
        return harvestDetailMapper.toDTO(harvestDetail);
    }

    /**
     * Gets all HarvestDetails.
     */
    @Override
    @Transactional(readOnly = true)
    public List<HarvestDetailResponseDTO> getAllHarvestDetails() {
        return harvestDetailRepository.findAll()
                .stream()
                .map(harvestDetailMapper::toDTO)
                .toList();
    }

    /**
     * Updates a HarvestDetail and recalculates the associated Harvest's total quantity.
     */
    @Override
    @Transactional
    public HarvestDetailResponseDTO updateHarvestDetail(Long id, HarvestDetailRequestDTO updateDTO) {
        HarvestDetail existingHarvestDetail = harvestDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("HarvestDetail not found"));

        if (updateDTO.quantity() != null) {
            existingHarvestDetail.setQuantity(updateDTO.quantity());
        }

        HarvestDetail updatedHarvestDetail = harvestDetailRepository.save(existingHarvestDetail);

        Harvest harvest = updatedHarvestDetail.getHarvest();

        harvestDetailMapper.updateEntityFromDTO(updateDTO, existingHarvestDetail);

        harvestRepository.save(harvest);

        return harvestDetailMapper.toDTO(updatedHarvestDetail);
    }

    /**
     * Deletes a HarvestDetail by its ID and updates the associated Harvest's total quantity.
     */
    @Override
    @Transactional
    public void deleteHarvestDetail(Long id) {
        HarvestDetail harvestDetail = harvestDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("HarvestDetail not found"));

        Harvest harvest = harvestDetail.getHarvest();

        harvest.setTotalQuantity(recalculateTotalQuantity(harvest));
        harvestRepository.save(harvest);

        harvestDetailRepository.delete(harvestDetail);

    }

    /**
     * Recalculates the total quantity for a given Harvest based on its HarvestDetails.
     */
    private Double recalculateTotalQuantity(Harvest harvest) {
        return harvest.getHarvestDetails()
                .stream()
                .mapToDouble(HarvestDetail::getQuantity)
                .sum();
    }
}
