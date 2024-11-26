package com.projet.citronix;

import com.projet.citronix.dto.harvestDetail.HarvestDetailRequestDTO;
import com.projet.citronix.dto.harvestDetail.HarvestDetailResponseDTO;
import com.projet.citronix.event.HarvestCreatedEvent;
import com.projet.citronix.exception.EntityNotFoundException;
import com.projet.citronix.mapper.HarvestDetailMapper;
import com.projet.citronix.model.Harvest;
import com.projet.citronix.model.HarvestDetail;
import com.projet.citronix.model.Tree;
import com.projet.citronix.repository.HarvestDetailRepository;
import com.projet.citronix.repository.HarvestRepository;
import com.projet.citronix.service.impl.HarvestDetailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HarvestDetailServiceImplTest {

    @Mock
    private HarvestDetailRepository harvestDetailRepository;

    @Mock
    private HarvestRepository harvestRepository;

    @Mock
    private HarvestDetailMapper harvestDetailMapper;

    @InjectMocks
    private HarvestDetailServiceImpl harvestDetailService;

    private Harvest harvest;
    private Tree tree;
    private HarvestDetail harvestDetail;

    @BeforeEach
    public void setUp() {
        harvest = new Harvest();
        harvest.setId(1L);
        harvest.setTotalQuantity(0.0);

        tree = new Tree();
        tree.setId(1L);
        tree.setProductivity(100.0);

        harvestDetail = new HarvestDetail();
        harvestDetail.setId(1L);
        harvestDetail.setQuantity(100.0);
        harvestDetail.setHarvest(harvest);
        harvestDetail.setTree(tree);
    }

    @Test
    void testAddHarvestDetail() {
        List<Tree> trees = Collections.singletonList(tree);
        HarvestCreatedEvent event = new HarvestCreatedEvent(harvest, trees);

        List<HarvestDetail> savedHarvestDetails = Collections.singletonList(harvestDetail);
        when(harvestDetailRepository.saveAll(anyList())).thenReturn(savedHarvestDetails);
        when(harvestRepository.save(any(Harvest.class))).thenReturn(harvest);
        when(harvestDetailMapper.toDTO(any(HarvestDetail.class)))
                .thenReturn(new HarvestDetailResponseDTO(
                        harvestDetail.getId(), null, null, harvestDetail.getQuantity()));

        List<HarvestDetailResponseDTO> result = harvestDetailService.addHarvestDetail(event);

        assertEquals(1, result.size());
        assertEquals(100.0, result.get(0).quantity());
        verify(harvestDetailRepository, times(1)).saveAll(anyList());
        verify(harvestRepository, times(1)).save(any(Harvest.class));
    }

    @Test
    void testGetHarvestDetailById() {
        when(harvestDetailRepository.findById(1L)).thenReturn(Optional.of(harvestDetail));
        when(harvestDetailMapper.toDTO(harvestDetail)).thenReturn(new HarvestDetailResponseDTO(
                harvestDetail.getId(), null, null, harvestDetail.getQuantity()));

        HarvestDetailResponseDTO result = harvestDetailService.getHarvestDetailById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(100.0, result.quantity());
        verify(harvestDetailRepository, times(1)).findById(1L);
    }

    @Test
    void testGetHarvestDetailById_NotFound() {
        when(harvestDetailRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> harvestDetailService.getHarvestDetailById(1L));
    }

    @Test
    void testGetAllHarvestDetails() {
        List<HarvestDetail> harvestDetails = Collections.singletonList(harvestDetail);
        when(harvestDetailRepository.findAll()).thenReturn(harvestDetails);
        when(harvestDetailMapper.toDTO(harvestDetail)).thenReturn(new HarvestDetailResponseDTO(
                harvestDetail.getId(), null, null, harvestDetail.getQuantity()));

        List<HarvestDetailResponseDTO> result = harvestDetailService.getAllHarvestDetails();

        assertEquals(1, result.size());
        assertEquals(100.0, result.get(0).quantity());
        verify(harvestDetailRepository, times(1)).findAll();
    }

    @Test
    void testUpdateHarvestDetail() {
        HarvestDetailRequestDTO updateDTO = new HarvestDetailRequestDTO(1L, 150.0);

        when(harvestDetailRepository.findById(1L)).thenReturn(Optional.of(harvestDetail));
        when(harvestDetailRepository.save(harvestDetail)).thenReturn(harvestDetail);
        when(harvestDetailMapper.toDTO(harvestDetail)).thenReturn(new HarvestDetailResponseDTO(
                harvestDetail.getId(), null, null, harvestDetail.getQuantity()));

        HarvestDetailResponseDTO result = harvestDetailService.updateHarvestDetail(1L, updateDTO);

        assertEquals(100.0, result.quantity());
        verify(harvestDetailRepository, times(1)).save(harvestDetail);
    }

    @Test
    void testUpdateHarvestDetail_NotFound() {
        HarvestDetailRequestDTO updateDTO = new HarvestDetailRequestDTO(1L, 150.0);

        when(harvestDetailRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> harvestDetailService.updateHarvestDetail(1L, updateDTO));
    }

    @Test
    void testDeleteHarvestDetail() {
        when(harvestDetailRepository.findById(1L)).thenReturn(Optional.of(harvestDetail));
        doNothing().when(harvestDetailRepository).delete(harvestDetail);
        when(harvestRepository.save(any(Harvest.class))).thenReturn(harvest);

        harvestDetailService.deleteHarvestDetail(1L);

        verify(harvestDetailRepository, times(1)).delete(harvestDetail);
        verify(harvestRepository, times(1)).save(any(Harvest.class));
    }

    @Test
    void testDeleteHarvestDetail_NotFound() {
        when(harvestDetailRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> harvestDetailService.deleteHarvestDetail(1L));
    }

    @Test
    void testRecalculateTotalQuantity() {
        harvest.setHarvestDetails(Collections.singletonList(harvestDetail));
        Double totalQuantity = harvestDetailService.recalculateTotalQuantity(harvest);

        assertEquals(100.0, totalQuantity);
    }
}
