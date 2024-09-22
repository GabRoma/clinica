package com.clinica.odontologica.exception;
/**
 * La clase `ResourceNotFoundException` es una excepción personalizada que se lanza cuando
 * no se encuentra un recurso en la aplicación. Extiende `RuntimeException`, lo que permite
 * que sea lanzada durante la ejecución sin necesidad de ser declarada explícitamente.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}