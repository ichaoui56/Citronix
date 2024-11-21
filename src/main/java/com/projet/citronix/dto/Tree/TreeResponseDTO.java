package com.projet.citronix.dto.Tree;

import com.projet.citronix.dto.field.emdb.EmdbFieldResponseDTO;

import java.time.LocalDate;

public record TreeResponseDTO (
        Long id,
        Integer age,
        LocalDate plantationDate,
        EmdbFieldResponseDTO field
) {
}
