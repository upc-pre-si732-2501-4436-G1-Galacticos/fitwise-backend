package org.upc.fitwise.iam.domain.exceptions;

public class InvalidVerificationCodeException extends RuntimeException {
    public InvalidVerificationCodeException() {
        super("Invalid or expired verification code.");
    }
}