package com.clinica.odontologica.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * La clase `ConflictException` es una excepción personalizada que se lanza cuando
 * ocurre un conflicto en la aplicación. Está anotada con `@ResponseStatus(HttpStatus.CONFLICT)`,
 * lo que indica que cuando se lanza esta excepción, el servidor debe responder con un
 * código de estado HTTP 409 (CONFLICT).
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException{
    public ConflictException(String message){
        super(message);
    }
}
