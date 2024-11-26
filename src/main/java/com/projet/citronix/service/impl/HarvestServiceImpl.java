package com.projet.citronix.service.impl;

import com.projet.citronix.dto.field.FieldResponseDTO;
import com.projet.citronix.dto.harvest.HarvestRequestDTO;
import com.projet.citronix.dto.harvest.HarvestResponseDTO;
import com.projet.citronix.event.HarvestCreatedEvent;
import com.projet.citronix.exception.EntityNotFoundException;
import com.projet.citronix.mapper.FieldMapper;
import com.projet.citronix.mapper.HarvestMapper;
import com.projet.citronix.model.Field;
import com.projet.citronix.model.Harvest;
import com.projet.citronix.model.Tree;
import com.projet.citronix.model.enums.SeasonType;
import com.projet.citronix.repository.FieldRepository;
import com.projet.citronix.repository.HarvestRepository;
import com.projet.citronix.service.HarvestService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final FieldRepository fieldRepository;
    private final HarvestMapper harvestMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final FieldMapper fieldMapper;

    @Override
    @Transactional
    public HarvestResponseDTO addHarvest(HarvestRequestDTO harvestRequestDTO) {
        Field field = retrieveFieldById(harvestRequestDTO.fieldId());
        SeasonType season = determineSeason(harvestRequestDTO.date());

        validateSeasonalHarvest(field, season);

        Double totalQuantity = calculateTotalQuantity(field.getTrees());
        Harvest harvest = createHarvest(harvestRequestDTO, field, season, totalQuantity);

        Harvest savedHarvest = harvestRepository.save(harvest);
        eventPublisher.publishEvent(new HarvestCreatedEvent(savedHarvest, field.getTrees()));

        return harvestMapper.toDTO(savedHarvest);
    }

    @Override
    public HarvestResponseDTO getHarvestById(Long id) {
        Harvest harvest = retrieveHarvestById(id);
        return harvestMapper.toDTO(harvest);
    }

    @Override
    public List<HarvestResponseDTO> getAllHarvests() {
        return harvestRepository.findAll()
                .stream()
                .map(harvestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HarvestResponseDTO updateHarvest(Long id, HarvestRequestDTO harvestRequestDTO) {
        Harvest existingHarvest = retrieveHarvestById(id);
        Field field = retrieveFieldById(harvestRequestDTO.fieldId());

        SeasonType season = determineSeason(harvestRequestDTO.date());
        Double totalQuantity = calculateTotalQuantity(field.getTrees());

        updateHarvestDetails(existingHarvest, harvestRequestDTO, field, season, totalQuantity);

        Harvest updatedHarvest = harvestRepository.save(existingHarvest);
        return harvestMapper.toDTO(updatedHarvest);
    }

    @Override
    @Transactional
    public void deleteHarvest(Long id) {
        Harvest harvest = retrieveHarvestById(id);
        harvestRepository.delete(harvest);
    }

    /**
         * Retrieves a Field by its ID and ensures it exists.
         */
    private Field retrieveFieldById(Long fieldId) {
        return fieldRepository.findById(fieldId)
                .orElseThrow(() -> new EntityNotFoundException("Field", fieldId));
    }

    /**
     * Retrieves a Harvest by its ID and ensures it exists.
     */
    private Harvest retrieveHarvestById(Long harvestId) {
        return harvestRepository.findById(harvestId)
                .orElseThrow(() -> new EntityNotFoundException("Harvest", harvestId));
    }

    private SeasonType determineSeason(LocalDate date) {
        int month = date.getMonthValue();
        if (month >= 3 && month <= 5) return SeasonType.SPRING;
        if (month >= 6 && month <= 8) return SeasonType.SUMMER;
        if (month >= 9 && month <= 11) return SeasonType.AUTUMN;
        return SeasonType.WINTER;
    }

    private void validateSeasonalHarvest(Field field, SeasonType season) {
        if (harvestRepository.existsByFieldAndSeason(field, season)) {
            throw new IllegalArgumentException("Field has already been harvested in this season");
        }
    }

    public Double calculateTotalQuantity(List<Tree> trees) {
        return trees.stream()
                .mapToDouble(Tree::getProductivity)
                .sum();
    }

    private Harvest createHarvest(HarvestRequestDTO harvestRequestDTO, Field field, SeasonType season, Double totalQuantity) {
        return Harvest.builder()
                .season(season)
                .totalQuantity(totalQuantity)
                .date(harvestRequestDTO.date())
                .field(field)
                .build();
    }

    private void updateHarvestDetails(Harvest harvest, HarvestRequestDTO updateDTO, Field field, SeasonType season, Double totalQuantity) {
        harvest.setSeason(season);
        harvest.setDate(updateDTO.date());
        harvest.setField(field);
        harvest.setTotalQuantity(totalQuantity);
    }
}
