package com.clinica.odontologica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * La clase `GlobalExceptionHandler` maneja las excepciones globalmente en la aplicación.
 * Utiliza `ControllerAdvice` para aplicar el manejo de excepciones a todos los controladores.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // Logger para registrar información y errores
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * Maneja las excepciones de validación de argumentos del método.
     * param ex La excepción `MethodArgumentNotValidException` lanzada cuando falla la validación.
     * return ResponseEntity con un mapa de errores y el estado HTTP 400 (BAD REQUEST).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.error("Error de validación: " + ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        // Recorre los errores de campo y los agrega al mapa de errores
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja las excepciones de recurso no encontrado.
     * param ex La excepción `ResourceNotFoundException` lanzada cuando no se encuentra un recurso.
     * param request El objeto `WebRequest` que contiene detalles de la solicitud.
     * return ResponseEntity con el mensaje de error y el estado HTTP 404 (NOT FOUND).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        logger.error("Error al buscar recurso: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja todas las demás excepciones no controladas específicamente.
     * param ex La excepción lanzada.
     * param request El objeto `WebRequest` que contiene detalles de la solicitud.
     * return ResponseEntity con el mensaje de error y el estado HTTP 500 (INTERNAL SERVER ERROR).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Error inesperado: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}