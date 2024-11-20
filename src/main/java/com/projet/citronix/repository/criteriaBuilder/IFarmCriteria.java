package com.projet.citronix.repository.criteriaBuilder;

import com.projet.citronix.model.Farm;

import java.util.List;
import java.util.Map;

public interface IFarmCriteria {
    List<Farm> findFarmsByCriteria(Map<String, Object> filters);
}
