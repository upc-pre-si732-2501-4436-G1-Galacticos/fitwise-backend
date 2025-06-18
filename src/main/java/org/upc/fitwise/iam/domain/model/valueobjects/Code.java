package org.upc.fitwise.iam.domain.model.valueobjects;


import jakarta.persistence.Embeddable;
import org.upc.fitwise.iam.domain.exceptions.InvalidVerificationCodeException;

import java.security.SecureRandom;

@Embeddable
public record Code(String verificationCode) {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();

    public Code {
        if (verificationCode == null || verificationCode.trim().isEmpty()) {
            throw new InvalidVerificationCodeException();
        }
    }

    public static Code generate() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return new Code(sb.toString());
    }

    @Override
    public String toString() {
        return verificationCode;
    }
}

