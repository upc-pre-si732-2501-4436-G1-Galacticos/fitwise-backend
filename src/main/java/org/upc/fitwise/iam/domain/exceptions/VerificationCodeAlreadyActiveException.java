package org.upc.fitwise.iam.domain.exceptions;

public class VerificationCodeAlreadyActiveException extends RuntimeException {
    public VerificationCodeAlreadyActiveException() {
        super("A verification token is already active. Please check your email.");
    }
}