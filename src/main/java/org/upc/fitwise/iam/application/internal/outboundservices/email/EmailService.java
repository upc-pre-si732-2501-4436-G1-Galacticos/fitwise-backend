package org.upc.fitwise.iam.application.internal.outboundservices.email;

import org.upc.fitwise.iam.domain.model.valueobjects.VerificationType;

public interface EmailService {
    void sendCodeToEmail(String email, String token, VerificationType verificationType);
}
