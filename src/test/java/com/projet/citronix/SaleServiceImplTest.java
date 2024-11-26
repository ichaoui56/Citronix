package com.projet.citronix;

import com.projet.citronix.dto.sale.SaleRequestDTO;
import com.projet.citronix.dto.sale.SaleResponseDTO;
import com.projet.citronix.exception.EntityNotFoundException;
import com.projet.citronix.mapper.SaleMapper;
import com.projet.citronix.model.Harvest;
import com.projet.citronix.model.Sale;
import com.projet.citronix.repository.HarvestRepository;
import com.projet.citronix.repository.SaleRepository;
import com.projet.citronix.service.impl.SaleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaleServiceImplTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private HarvestRepository harvestRepository;

    @Mock
    private SaleMapper saleMapper;

    @InjectMocks
    private SaleServiceImpl saleService;

    private Harvest harvest;
    private Sale sale;
    private SaleRequestDTO saleRequestDTO;
    private SaleResponseDTO saleResponseDTO;

    @BeforeEach
    void setUp() {
        harvest = new Harvest();
        harvest.setId(1L);
        harvest.setTotalQuantity(100.0);

        sale = new Sale();
        sale.setId(1L);
        sale.setDate(LocalDate.now());
        sale.setUnitPrice(10.0);
        sale.setQuantity(10.0);
        sale.setClient("Client 1");
        sale.setHarvest(harvest);

        saleRequestDTO = new SaleRequestDTO(
                LocalDate.now(),
                10.0,
                10.0,
                "Client 1",
                1L
        );

        saleResponseDTO = new SaleResponseDTO(
                1L,
                LocalDate.now(),
                10.0,
                10.0,
                "Client 1",
                100.0,
                null
        );
    }

    @Test
    void addSale_shouldReturnSaleResponseDTO_whenValidRequest() {
        when(harvestRepository.findById(1L)).thenReturn(Optional.of(harvest));
        when(saleMapper.toEntity(saleRequestDTO)).thenReturn(sale);
        when(saleMapper.toDTO(sale)).thenReturn(saleResponseDTO);
        when(saleRepository.save(sale)).thenReturn(sale);

        SaleResponseDTO response = saleService.addSale(saleRequestDTO);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Client 1", response.client());
        verify(harvestRepository, times(1)).findById(1L);
        verify(saleRepository, times(1)).save(sale);
    }

    @Test
    void addSale_shouldThrowEntityNotFoundException_whenHarvestNotFound() {
        when(harvestRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                saleService.addSale(saleRequestDTO));
        assertEquals("Harvest not found with ID: 1", exception.getMessage());
    }

    @Test
    void findById_shouldReturnSaleResponseDTO_whenSaleExists() {
        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));
        when(saleMapper.toDTO(sale)).thenReturn(saleResponseDTO);

        SaleResponseDTO response = saleService.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Client 1", response.client());
        verify(saleRepository, times(1)).findById(1L);
    }

    @Test
    void findById_shouldThrowEntityNotFoundException_whenSaleNotFound() {
        when(saleRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                saleService.findById(1L));
        assertEquals("Sale not found with ID: 1", exception.getMessage());
    }

    @Test
    void updateSale_shouldReturnUpdatedSaleResponseDTO_whenValidRequest() {
        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));
        when(harvestRepository.findById(1L)).thenReturn(Optional.of(harvest));
        when(saleMapper.toDTO(sale)).thenReturn(saleResponseDTO);

        doNothing().when(saleMapper).updateEntityFromDTO(saleRequestDTO, sale);

        when(saleRepository.save(sale)).thenReturn(sale);

        SaleResponseDTO response = saleService.updateSale(1L, saleRequestDTO);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Client 1", response.client());
        verify(saleRepository, times(1)).findById(1L);
        verify(saleRepository, times(1)).save(sale);
    }
}
