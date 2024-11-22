package com.projet.citronix.repository;

import com.projet.citronix.model.Field;
import com.projet.citronix.model.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Long> {
    boolean existsByFieldAndSeason(Field field, String season);
}
