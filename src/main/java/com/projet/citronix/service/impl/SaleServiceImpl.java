package com.projet.citronix.service.impl;

import com.projet.citronix.dto.sale.SaleRequestDTO;
import com.projet.citronix.dto.sale.SaleResponseDTO;
import com.projet.citronix.mapper.SaleMapper;
import com.projet.citronix.mapper.SaleMapperImpl;
import com.projet.citronix.model.Harvest;
import com.projet.citronix.model.Sale;
import com.projet.citronix.repository.HarvestRepository;
import com.projet.citronix.repository.SaleRepository;
import com.projet.citronix.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final HarvestRepository harvestRepository;
    private final SaleMapper saleMapper;
    private final SaleMapperImpl saleMapperImpl;

    @Override
    @Transactional
    public SaleResponseDTO addSale(SaleRequestDTO saleRequestDTO) {
        Harvest harvest = validateAndRetrieveHarvest(saleRequestDTO.harvestId(), saleRequestDTO.quantity());
        Sale sale = createAndSaveSale(saleRequestDTO, harvest);
        updateHarvestQuantity(harvest, saleRequestDTO.quantity());
        return saleMapper.toDTO(sale);
    }

    @Override
    public SaleResponseDTO finById(Long saleId){
        Sale sale = retrieveSaleById(saleId);
        return saleMapper.toDTO(sale);
    }

    @Override
    public List<SaleResponseDTO> findAll(){
        return saleRepository.findAll()
                .stream()
                .map(saleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteSale(Long id){
          Sale sale = retrieveSaleById(id);
          saleRepository.delete(sale);
    }

    @Override
    @Transactional
    public SaleResponseDTO updateSale(Long id, SaleRequestDTO requestDTO){
        Sale sale = retrieveSaleById(id);
        Harvest harvest = validateAndRetrieveHarvest(requestDTO.harvestId(), requestDTO.quantity());
        updateHarvestQuantity(harvest, requestDTO.quantity());

        saleMapper.updateEntityFromDTO(requestDTO,sale);

        Sale updatedSale = saleRepository.save(sale);

        return saleMapper.toDTO(updatedSale);
    }

    public Sale retrieveSaleById(Long id){
        return saleRepository.findById(id)
                .orElseThrow(()->new RuntimeException("sale was not found with this " + id));
    }

    /**
     * Validates and retrieves the harvest associated with the given ID.
     */
    private Harvest validateAndRetrieveHarvest(Long harvestId, Double quantity) {
        Harvest harvest = harvestRepository.findById(harvestId)
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found with ID: " + harvestId));

        if (harvest.getTotalQuantity() < quantity) {
            throw new IllegalStateException("Insufficient harvest quantity. Available: " + harvest.getTotalQuantity());
        }
        return harvest;
    }

    /**
     * Creates and saves a Sale entity based on the provided SaleRequestDTO and associated Harvest.
     */
    private Sale createAndSaveSale(SaleRequestDTO saleRequestDTO, Harvest harvest) {
        Sale sale = saleMapper.toEntity(saleRequestDTO);
        sale.setHarvest(harvest);
        return saleRepository.save(sale);
    }

    /**
     * Updates the quantity of the Harvest after a sale.
     */
    private void updateHarvestQuantity(Harvest harvest, Double quantitySold) {
        harvest.setTotalQuantity(harvest.getTotalQuantity() - quantitySold);
        harvestRepository.save(harvest);
    }
}
