package org.upc.fitwise.iam.domain.model.aggregates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class EmailVerification extends AuditableAbstractAggregateRoot<EmailVerification> {
    @Email
    @NotBlank
    @Getter
    @Size(max = 120)
    private String email;

    @Getter
    @NotBlank
    @Size(max = 120)
    @Column(unique = true)
    private String token;

    private LocalDateTime expirationDate;

    private  Boolean verified;

    public EmailVerification() {
    }

    public EmailVerification(String email) {
        this.email = email;
        this.token = UUID.randomUUID().toString();
        this.expirationDate = LocalDateTime.now().plusHours(1);
        this.verified = false;
    }



    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationDate);
    }

    public void verify(String token) {
        if(isVerified()){
            throw new RuntimeException("Token already verified");
        }
        if (isExpired()) {
            throw new RuntimeException("Token expired");
        }

        if (!this.token.equals(token)) {
            throw new RuntimeException("Invalid token");
        }
        this.verified = true;
    }
    public void expireNow(){
        this.expirationDate = LocalDateTime.now();
    }

    public String getVerificationToken() {
        return token;
    }

    public boolean isVerified() {
        return verified;
    }


}
