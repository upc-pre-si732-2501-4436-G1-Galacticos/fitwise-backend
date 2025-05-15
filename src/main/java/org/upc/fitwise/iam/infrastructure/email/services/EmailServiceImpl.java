package org.upc.fitwise.iam.infrastructure.email.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.upc.fitwise.iam.application.internal.outboundservices.email.EmailService;
import org.upc.fitwise.iam.domain.model.valueobjects.VerificationType;
import org.springframework.beans.factory.annotation.Value;
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    @Value("${frontend.url}")
    private String frontendUrl;
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendCodeToEmail(String email, String token, VerificationType verificationType) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        if(verificationType == VerificationType.SIGN_IN){
            message.setSubject("Email verification");
            message.setText("Hello your token for sesion is " + token+"&email=" + email);
        }
        if(verificationType == VerificationType.PASSWORD_RESET){
            message.setSubject("Email verification");
            message.setText("Click this link to reset your password: "
                    + frontendUrl + "/reset-password?token=" + token + "&email=" + email);

        }

        mailSender.send(message);
    }
}
