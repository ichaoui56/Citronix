package com.projet.citronix.service;

import com.projet.citronix.dto.sale.SaleRequestDTO;
import com.projet.citronix.dto.sale.SaleResponseDTO;

public interface SaleService {
    SaleResponseDTO addSale(SaleRequestDTO saleRequestDTO);
}
