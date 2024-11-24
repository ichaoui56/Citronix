package com.projet.citronix.service;

import com.projet.citronix.dto.sale.SaleRequestDTO;
import com.projet.citronix.dto.sale.SaleResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SaleService {
    SaleResponseDTO addSale(SaleRequestDTO saleRequestDTO);

    SaleResponseDTO finById(Long saleId);

    List<SaleResponseDTO> findAll();

    @Transactional
    void deleteSale(Long id);

    @Transactional
    SaleResponseDTO updateSale(Long id, SaleRequestDTO requestDTO);
}
