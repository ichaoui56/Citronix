package com.projet.citronix.repository.criteriaBuilder.impl;

import com.projet.citronix.model.Farm;
import com.projet.citronix.repository.criteriaBuilder.IFarmCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Repository
public class FarmCriteriaImpl implements IFarmCriteria {

    private final EntityManager em;

    @Override
    public List<Farm> findFarmsByCriteria(Map<String, Object> filters) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Farm> criteriaQuery = cb.createQuery(Farm.class);
        Root<Farm> farmRoot = criteriaQuery.from(Farm.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filters.containsKey("name")) {
            predicates.add(cb.like(farmRoot.get("name"), "%" + filters.get("name") + "%"));
        }

        if (filters.containsKey("location")) {
            predicates.add(cb.like(farmRoot.get("location"), "%" + filters.get("location") + "%"));
        }

        if (filters.containsKey("size")) {
            predicates.add(cb.equal(farmRoot.get("size"), filters.get("size")));
        }

        if (filters.containsKey("createdDateAfter")) {
            String dateStr = (String) filters.get("createdDateAfter");
            java.time.LocalDate date = java.time.LocalDate.parse(dateStr);
            predicates.add(cb.greaterThanOrEqualTo(farmRoot.get("createdDate"), date));
        }

        criteriaQuery.select(farmRoot)
                .where(cb.and(predicates.toArray(new Predicate[0])));

        return em.createQuery(criteriaQuery).getResultList();
    }
}
