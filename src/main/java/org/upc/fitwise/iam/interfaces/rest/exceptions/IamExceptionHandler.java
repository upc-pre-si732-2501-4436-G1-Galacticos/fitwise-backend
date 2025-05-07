package org.upc.fitwise.iam.interfaces.rest.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "org.upc.fitwise.iam")
public class IamExceptionHandler {

    // ⚠️ Validación de @Valid fallida en el body del request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        err -> err.getField(),
                        err -> err.getDefaultMessage(),
                        (msg1, msg2) -> msg1 + "; " + msg2 // en caso de conflictos
                ));
        return new ResponseEntity<>(buildResponse("Validation failed", errors), HttpStatus.BAD_REQUEST);
    }

    // ⚠️ Validación fallida de constraints tipo @Email, @NotNull en entidades
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        var errors = ex.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        err -> err.getPropertyPath().toString(),
                        err -> err.getMessage(),
                        (msg1, msg2) -> msg1 + "; " + msg2
                ));

        return new ResponseEntity<>(buildResponse("Constraint violation", errors), HttpStatus.BAD_REQUEST);
    }

    // ⚠️ JSON mal formado, campos incorrectos, etc.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleInvalidJson(HttpMessageNotReadableException ex, WebRequest request) {
        return new ResponseEntity<>(
                buildResponse("Malformed JSON", Map.of("error", ex.getLocalizedMessage())),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST
        );
    }

    // ⚠️ Manejo genérico para cualquier excepción no controlada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return new ResponseEntity<>(
                buildResponse("Internal error", Map.of("error", ex.getMessage())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


    private Map<String, Object> buildResponse(String message, Map<String, String> errors) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("errors", errors);
        return response;
    }
}
