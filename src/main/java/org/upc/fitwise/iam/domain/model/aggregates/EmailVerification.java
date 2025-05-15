package org.upc.fitwise.iam.domain.model.aggregates;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.upc.fitwise.iam.domain.model.valueobjects.Code;
import org.upc.fitwise.iam.domain.model.valueobjects.VerificationType;
import org.upc.fitwise.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDateTime;

@Entity
public class EmailVerification extends AuditableAbstractAggregateRoot<EmailVerification> {
    @Email
    @NotBlank
    @Getter
    @Size(max = 120)
    private String email;

    @Embedded
    @Getter
    @Column(unique = true)
    private Code verificationCode;

    private VerificationType verificationType;



    private LocalDateTime expirationDate;

    private  Boolean verified;

    public EmailVerification() {
    }
    public EmailVerification(String email, VerificationType verificationType) {
        this.email = email;
        this.verificationCode = Code.generate();
        this.verificationType = verificationType;
        this.expirationDate = LocalDateTime.now().plusMinutes(15);
        this.verified = false;
    }



    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationDate);
    }

    public void validateOrThrow(Code verificationCode) {
        if(isVerified()){
            throw new RuntimeException("Token already verified");
        }
        if (isExpired()) {
            throw new RuntimeException("Token expired");
        }

        if (!this.verificationCode.equals(verificationCode)) {
            throw new RuntimeException("Invalid token");
        }
        this.verified = true;
    }
    public void expireNow(){
        this.expirationDate = LocalDateTime.now();
    }

    public boolean isVerified() {
        return verified;
    }


}

