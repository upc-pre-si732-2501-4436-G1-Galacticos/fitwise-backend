package org.upc.fitwise.plan.domain.exceptions;

public class DietNotFoundException extends RuntimeException { // Corregido: 'class' y 'extends RuntimeException'
    public DietNotFoundException(Long dietId) { // Corregido: Parámetro en el constructor
        super("Diet with ID " + dietId + " not found.");
    }
}