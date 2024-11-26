package com.projet.citronix;

import com.projet.citronix.dto.farm.FarmRequestDTO;
import com.projet.citronix.dto.farm.FarmResponseDTO;
import com.projet.citronix.dto.farm.FarmSearchDTO;
import com.projet.citronix.dto.field.emdb.EmdbFieldResponseDTO;
import com.projet.citronix.exception.EntityNotFoundException;
import com.projet.citronix.exception.SearchNotFoundException;
import com.projet.citronix.mapper.FarmMapper;
import com.projet.citronix.model.Farm;
import com.projet.citronix.repository.FarmRepository;
import com.projet.citronix.repository.criteriaBuilder.IFarmCriteria;
import com.projet.citronix.service.impl.FarmServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FarmServiceImplTest {

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FarmMapper farmMapper;

    @Mock
    private IFarmCriteria farmCriteria;

    @InjectMocks
    private FarmServiceImpl farmService;

    private Farm farm;
    private FarmRequestDTO farmRequestDTO;
    private FarmResponseDTO farmResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        farm = Farm.builder()
                .id(1L)
                .name("Lemon Farm")
                .location("Safi, Morocco")
                .size(1500.0)
                .createdDate(LocalDate.of(2023, 1, 1))
                .fields(Collections.emptyList())
                .build();

        List<EmdbFieldResponseDTO> fields = List.of(
                new EmdbFieldResponseDTO(1L, "Field A", 500.0)
        );

        farmRequestDTO = new FarmRequestDTO("Lemon Farm", "Safi, Morocco", 1500.0, LocalDate.of(2023, 1, 1));
        farmResponseDTO = new FarmResponseDTO(
                1L,
                "Lemon Farm",
                "Safi, Morocco",
                1500.0,
                LocalDate.of(2023, 1, 1),
                fields
        );
    }

    @Test
    void addFarm_ReturnsFarmResponseDTO_WhenSuccessful() {
        when(farmMapper.toEntity(farmRequestDTO)).thenReturn(farm);
        when(farmRepository.save(farm)).thenReturn(farm);
        when(farmMapper.toDTO(farm)).thenReturn(farmResponseDTO);

        FarmResponseDTO result = farmService.addFarm(farmRequestDTO);

        assertNotNull(result);
        assertEquals(farmResponseDTO, result);
        verify(farmRepository).save(farm);
    }

    @Test
    void updateFarm_ReturnsUpdatedFarmResponseDTO_WhenFarmExists() {
        when(farmRepository.findById(farm.getId())).thenReturn(Optional.of(farm));
        when(farmMapper.toDTO(any(Farm.class))).thenReturn(farmResponseDTO);
        when(farmRepository.save(any(Farm.class))).thenReturn(farm);

        FarmResponseDTO result = farmService.updateFarm(farm.getId(), farmRequestDTO);

        assertNotNull(result);
        assertEquals("Lemon Farm", result.name());
        assertEquals(1500.0, result.size());
        verify(farmRepository).save(farm);
    }

    @Test
    void updateFarm_ThrowsException_WhenFarmSizeIsTooSmall() {
        farmRequestDTO = new FarmRequestDTO(
                "Small Farm",
                "Location C",
                500.0,
                LocalDate.of(2023, 1, 1)
        );

        when(farmRepository.findById(farm.getId())).thenReturn(Optional.of(farm));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> farmService.updateFarm(farm.getId(), farmRequestDTO));

        assertEquals("Farm area size must not be less than 1000.", exception.getMessage());
        verify(farmRepository, never()).save(any(Farm.class));
    }

    @Test
    void updateFarm_ThrowsEntityNotFoundException_WhenFarmDoesNotExist() {
        when(farmRepository.findById(farm.getId())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> farmService.updateFarm(farm.getId(), farmRequestDTO));

        assertEquals("Field not found with ID: 1", exception.getMessage());
        verify(farmRepository, never()).save(any(Farm.class));
    }

    @Test
    void removeFarm_ReturnsTrue_WhenFarmExists() {
        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));

        boolean result = farmService.removeFarm(1L);

        assertTrue(result);
        verify(farmRepository).delete(farm);
    }

    @Test
    void removeFarm_ThrowsEntityNotFoundException_WhenFarmDoesNotExist() {
        when(farmRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> farmService.removeFarm(1L));
        assertEquals("Field not found with ID: 1", exception.getMessage());
    }

    @Test
    void getAllFarms_ReturnsListOfFarmResponseDTO_WhenFarmsExist() {
        List<Farm> farms = List.of(farm);
        when(farmRepository.findAll()).thenReturn(farms);
        when(farmMapper.toDTO(farm)).thenReturn(farmResponseDTO);

        List<FarmResponseDTO> result = farmService.getAllFarms();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(farmResponseDTO, result.get(0));
    }

    @Test
    void getFarmById_ReturnsFarmResponseDTO_WhenFarmExists() {
        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(farmMapper.toDTO(farm)).thenReturn(farmResponseDTO);

        FarmResponseDTO result = farmService.getFarmById(1L);

        assertNotNull(result);
        assertEquals(farmResponseDTO, result);
    }

    @Test
    void getFarmById_ThrowsEntityNotFoundException_WhenFarmDoesNotExist() {
        when(farmRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> farmService.getFarmById(1L));
        assertEquals("Field not found with ID: 1", exception.getMessage());
    }

    @Test
    void searchFarmsByNameAndLocation_ReturnsListOfFarmResponseDTO_WhenMatchingFarmsExist() {
        Map<String, Object> filters = new HashMap<>();
        filters.put("name", "Lemon Farm");
        filters.put("location", "Safi, Morocco");

        when(farmCriteria.findFarmsByCriteria(filters)).thenReturn(List.of(farm));
        when(farmMapper.toDTO(farm)).thenReturn(farmResponseDTO);

        FarmSearchDTO searchDTO = new FarmSearchDTO("Lemon Farm", "Safi, Morocco", null, null);
        List<FarmResponseDTO> result = farmService.getAllFarmsByNameAndLocalisation(searchDTO);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(farmResponseDTO, result.get(0));
    }

    @Test
    void searchFarmsByNameAndLocation_ThrowsSearchNotFoundException_WhenNoMatchingFarmsExist() {
        Map<String, Object> filters = new HashMap<>();
        filters.put("name", "Nonexistent Farm");

        when(farmCriteria.findFarmsByCriteria(filters)).thenReturn(Collections.emptyList());

        FarmSearchDTO searchDTO = new FarmSearchDTO("Nonexistent Farm", null, null, null);

        Exception exception = assertThrows(SearchNotFoundException.class, () -> farmService.getAllFarmsByNameAndLocalisation(searchDTO));
        assertEquals("No farms found matching the criteria.", exception.getMessage());
    }
}
