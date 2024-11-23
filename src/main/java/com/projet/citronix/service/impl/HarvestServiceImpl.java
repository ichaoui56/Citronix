package com.projet.citronix.service.impl;

import com.projet.citronix.dto.harvest.HarvestRequestDTO;
import com.projet.citronix.dto.harvest.HarvestResponseDTO;
import com.projet.citronix.dto.harvest.HarvestUpdateDTO;
import com.projet.citronix.event.HarvestCreatedEvent;
import com.projet.citronix.mapper.HarvestMapper;
import com.projet.citronix.model.Field;
import com.projet.citronix.model.Harvest;
import com.projet.citronix.model.Tree;
import com.projet.citronix.model.enums.SeasonType;
import com.projet.citronix.repository.FieldRepository;
import com.projet.citronix.repository.HarvestRepository;
import com.projet.citronix.service.HarvestService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HarvestServiceImpl implements HarvestService {

    private HarvestRepository harvestRepository;
    private FieldRepository fieldRepository;
    private HarvestMapper harvestMapper;
    private ApplicationEventPublisher eventPublisher;

    public HarvestResponseDTO addHarvest(HarvestRequestDTO harvestRequestDTO) {
        Field field = fieldRepository.findById(harvestRequestDTO.fieldId())
                .orElseThrow(() -> new IllegalArgumentException("Field not found"));

        String season = getSeasonForDate(harvestRequestDTO.date());

        if (harvestRepository.existsByFieldAndSeason(field, season)) {
            throw new IllegalArgumentException("Field has already been harvested in this season");
        }

        Double totalQuantity = calculateTotalQuantity(field.getTrees());

        Harvest harvest = Harvest.builder()
                .season(season)
                .totalQuantity(totalQuantity)
                .date(harvestRequestDTO.date())
                .field(field)
                .build();

        harvest = harvestRepository.save(harvest);
        eventPublisher.publishEvent(new HarvestCreatedEvent(harvest,field.getTrees()));
        return harvestMapper.toDTO(harvest);
    }

    public HarvestResponseDTO getHarvestById(Long id) {
        Harvest harvest = harvestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found"));
        return harvestMapper.toDTO(harvest);
    }

    public List<HarvestResponseDTO> getAllHarvests() {
        List<Harvest> harvests = harvestRepository.findAll();
        return harvests.stream()
                .map(harvestMapper::toDTO)
                .collect(Collectors.toList());
    }

    public HarvestResponseDTO updateHarvest(Long id, HarvestUpdateDTO harvestUpdateDTO) {
        Harvest existingHarvest = harvestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found"));

        Field field = fieldRepository.findById(harvestUpdateDTO.fieldId())
                .orElseThrow(() -> new IllegalArgumentException("Field not found"));

        existingHarvest.setSeason(getSeasonForDate(harvestUpdateDTO.date()));
        existingHarvest.setDate(harvestUpdateDTO.date());
        existingHarvest.setField(field);

        Double totalQuantity = calculateTotalQuantity(field.getTrees());
        existingHarvest.setTotalQuantity(totalQuantity);

        existingHarvest = harvestRepository.save(existingHarvest);
        return harvestMapper.toDTO(existingHarvest);
    }

    public void deleteHarvest(Long id) {
        Harvest harvest = harvestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found"));
        harvestRepository.delete(harvest);
    }

    private String getSeasonForDate(LocalDate date) {
        int month = date.getMonthValue();
        if (month >= 3 && month <= 5) {
            return SeasonType.SPRING.name();
        } else if (month >= 6 && month <= 8) {
            return SeasonType.SUMMER.name();
        } else if (month >= 9 && month <= 11) {
            return SeasonType.AUTUMN.name();
        } else {
            return SeasonType.WINTER.name();
        }
    }

    private Double calculateTotalQuantity(List<Tree> trees) {
        return trees.stream()
                .mapToDouble(Tree::getProductivity)
                .sum();
    }
}
