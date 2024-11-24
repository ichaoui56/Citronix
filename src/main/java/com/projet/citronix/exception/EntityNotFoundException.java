package com.projet.citronix.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity, Long id) {
        super(entity + " not found with ID: " + id);
    }
}
