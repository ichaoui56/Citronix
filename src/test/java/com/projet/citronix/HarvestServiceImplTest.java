package com.projet.citronix;

import com.projet.citronix.dto.harvest.HarvestRequestDTO;
import com.projet.citronix.dto.harvest.HarvestResponseDTO;
import com.projet.citronix.event.HarvestCreatedEvent;
import com.projet.citronix.mapper.HarvestMapper;
import com.projet.citronix.model.Field;
import com.projet.citronix.model.Harvest;
import com.projet.citronix.model.Tree;
import com.projet.citronix.model.enums.SeasonType;
import com.projet.citronix.repository.FieldRepository;
import com.projet.citronix.repository.HarvestRepository;
import com.projet.citronix.exception.EntityNotFoundException;
import com.projet.citronix.service.impl.HarvestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HarvestServiceImplTest {

    @Mock
    private HarvestRepository harvestRepository;

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private HarvestMapper harvestMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private HarvestServiceImpl harvestService;

    private Field field;
    private Harvest harvest;
    private HarvestRequestDTO harvestRequestDTO;

    @BeforeEach
    public void setUp() {
        field = Field.builder().id(1L).name("Field 1").build();
        harvest = Harvest.builder()
                .id(1L)
                .date(LocalDate.of(2024, 5, 1))
                .season(SeasonType.SPRING)
                .totalQuantity(100.0)
                .field(field)
                .build();

        harvestRequestDTO = new HarvestRequestDTO(1L, LocalDate.of(2024, 5, 1));
    }

    @Test
    public void shouldThrowException_whenFieldHasAlreadyBeenHarvestedInSeason() {
        when(fieldRepository.findById(1L)).thenReturn(Optional.of(field));
        when(harvestRepository.existsByFieldAndSeason(any(), any())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> harvestService.addHarvest(harvestRequestDTO));

        assertEquals("Field has already been harvested in this season", exception.getMessage());
    }

    @Test
    public void shouldGetHarvestByIdSuccessfully_whenHarvestExists() {
        when(harvestRepository.findById(1L)).thenReturn(Optional.of(harvest));
        when(harvestMapper.toDTO(any(Harvest.class))).thenReturn(new HarvestResponseDTO(1L,  SeasonType.SPRING, 100.0, null));

        HarvestResponseDTO response = harvestService.getHarvestById(1L);

        assertNotNull(response);
        assertEquals(100.0, response.totalQuantity());
    }

    @Test
    public void shouldThrowException_whenHarvestNotFoundById() {
        when(harvestRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> harvestService.getHarvestById(1L));

        assertEquals("Harvest not found with ID: 1", exception.getMessage());
    }

    @Test
    public void shouldDeleteHarvestSuccessfully_whenHarvestExists() {
        when(harvestRepository.findById(1L)).thenReturn(Optional.of(harvest));

        harvestService.deleteHarvest(1L);

        verify(harvestRepository, times(1)).delete(any(Harvest.class));
    }

    @Test
    public void shouldThrowException_whenHarvestNotFoundToDelete() {
        when(harvestRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> harvestService.deleteHarvest(1L));

        assertEquals("Harvest not found with ID: 1", exception.getMessage());
    }

}
