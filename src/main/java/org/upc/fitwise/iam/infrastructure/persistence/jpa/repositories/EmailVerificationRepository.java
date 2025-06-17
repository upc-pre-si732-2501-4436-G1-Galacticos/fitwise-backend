package org.upc.fitwise.iam.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.upc.fitwise.iam.domain.model.aggregates.EmailVerification;
import org.upc.fitwise.iam.domain.model.valueobjects.Code;
import org.upc.fitwise.iam.domain.model.valueobjects.VerificationType;


import java.util.Optional;


public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
   Optional<EmailVerification> findFirstByEmailAndVerifiedFalseAndVerificationTypeOrderByCreatedAtDesc(String email, VerificationType type);
    Optional<EmailVerification> findFirstByVerificationCodeAndVerifiedFalseAndVerificationTypeOrderByCreatedAtDesc(Code token, VerificationType type);


}

