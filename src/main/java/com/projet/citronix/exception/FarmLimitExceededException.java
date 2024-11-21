package com.projet.citronix.exception;

public class FarmLimitExceededException extends RuntimeException {
  public FarmLimitExceededException(String message) {
    super(message);
  }
}
