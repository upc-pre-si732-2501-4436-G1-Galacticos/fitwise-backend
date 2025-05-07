package org.upc.fitwise.iam.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.upc.fitwise.iam.domain.model.aggregates.EmailVerification;

import java.time.LocalDateTime;
import java.util.Optional;


public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findByToken(String token);
    Optional<EmailVerification> findFirstByEmailAndExpirationDateAfterAndVerifiedFalseOrderByCreatedAtDesc(
            String email, LocalDateTime now);
}

