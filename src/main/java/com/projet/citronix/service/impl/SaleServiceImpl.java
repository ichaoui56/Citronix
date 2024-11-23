package com.projet.citronix.service.impl;

import com.projet.citronix.dto.sale.SaleRequestDTO;
import com.projet.citronix.dto.sale.SaleResponseDTO;
import com.projet.citronix.mapper.SaleMapper;
import com.projet.citronix.model.Harvest;
import com.projet.citronix.model.Sale;
import com.projet.citronix.repository.HarvestRepository;
import com.projet.citronix.repository.SaleRepository;
import com.projet.citronix.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final HarvestRepository harvestRepository;
    private final SaleMapper saleMapper;

    @Override
    public SaleResponseDTO addSale(SaleRequestDTO saleRequestDTO) {
        Harvest harvest = harvestRepository.findById(saleRequestDTO.harvestId())
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found with ID: " + saleRequestDTO.harvestId()));

        Sale sale = saleMapper.toEntity(saleRequestDTO);
        sale.setHarvest(harvest);
        sale = saleRepository.save(sale);

        return saleMapper.toDTO(sale);
    }
}
