package org.upc.fitwise.iam.domain.exceptions;

public class VerificationCodeExpiredException extends RuntimeException {
    public VerificationCodeExpiredException() {
        super("Verification code has expired.");
    }
}