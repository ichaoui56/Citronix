package com.projet.citronix.dto.Tree.emdb;

import com.projet.citronix.dto.field.emdb.EmdbFieldResponseDTO;

import java.time.LocalDate;

public record EmdbTreeResponseDTO(
        Long id,
        Integer age,
        Double productivity,
        LocalDate plantationDate
) {
}
