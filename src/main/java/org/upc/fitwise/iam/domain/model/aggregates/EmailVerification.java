package org.upc.fitwise.iam.domain.model.aggregates;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.upc.fitwise.iam.domain.exceptions.InvalidVerificationCodeException;
import org.upc.fitwise.iam.domain.exceptions.VerificationCodeAlreadyUsedException;
import org.upc.fitwise.iam.domain.exceptions.VerificationCodeExpiredException;
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

    @Setter
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

    public boolean isCodeInvalid(Code verificationCode){
        return !verificationCode.equals(verificationCode);
    }
    public void expireNow(){
        this.expirationDate = LocalDateTime.now();
    }

    public boolean isVerified() {
        return verified;
    }

}

