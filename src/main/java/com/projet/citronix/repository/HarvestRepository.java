package com.projet.citronix.repository;

import com.projet.citronix.model.Field;
import com.projet.citronix.model.Harvest;
import com.projet.citronix.model.enums.SeasonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Long> {
    boolean existsByFieldAndSeason(Field field, SeasonType season);
}
