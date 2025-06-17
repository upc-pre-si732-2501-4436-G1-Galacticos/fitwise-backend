package org.upc.fitwise.iam.domain.exceptions;

public class VerificationCodeAlreadyUsedException extends RuntimeException {
    public VerificationCodeAlreadyUsedException() {
        super("Verification code has already been used.");
    }
}