package com.projet.citronix.repository;

import com.projet.citronix.model.Sale;
import com.projet.citronix.model.enums.SeasonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
}
