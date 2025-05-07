package org.upc.fitwise.iam.application.internal.outboundservices.email;

public interface EmailService {
    void sendPasswordResetEmail(String email, String token);
}
