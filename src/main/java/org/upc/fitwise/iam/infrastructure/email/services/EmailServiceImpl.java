package org.upc.fitwise.iam.infrastructure.email.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.upc.fitwise.iam.application.internal.outboundservices.email.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendPasswordResetEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Email verification");
        message.setText("Click this link to verify: http://example.com/verify?token=" + token);
        mailSender.send(message);
    }
}
