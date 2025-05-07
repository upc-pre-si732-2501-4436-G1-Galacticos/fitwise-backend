package org.upc.fitwise.iam.application.internal.commandservices;

import org.upc.fitwise.iam.application.internal.outboundservices.email.EmailService;
import org.upc.fitwise.iam.application.internal.outboundservices.hashing.HashingService;
import org.upc.fitwise.iam.application.internal.outboundservices.tokens.TokenService;
import org.upc.fitwise.iam.domain.model.aggregates.EmailVerification;
import org.upc.fitwise.iam.domain.model.aggregates.User;
import org.upc.fitwise.iam.domain.model.commands.ForgotPasswordCommand;
import org.upc.fitwise.iam.domain.model.commands.ResetPasswordCommand;
import org.upc.fitwise.iam.domain.model.commands.SignInCommand;
import org.upc.fitwise.iam.domain.model.commands.SignUpCommand;
import org.upc.fitwise.iam.domain.model.entities.Role;
import org.upc.fitwise.iam.domain.model.valueobjects.Roles;
import org.upc.fitwise.iam.domain.services.UserCommandService;
import org.upc.fitwise.iam.infrastructure.persistence.jpa.repositories.EmailVerificationRepository;
import org.upc.fitwise.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.upc.fitwise.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailService emailService;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService, RoleRepository roleRepository, EmailService emailService,EmailVerificationRepository emailVerificationRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
        this.emailVerificationRepository = emailVerificationRepository;
    }

    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByEmail(command.email()))
            throw new RuntimeException("Email already exists");
        var stringRoles = command.roles();
        var roles = new ArrayList<Role>();
        if (stringRoles == null || stringRoles.isEmpty()) {
            var storedRole = roleRepository.findByName(Roles.ROLE_USER);
            storedRole.ifPresent(roles::add);
        } else {
            stringRoles.forEach(role -> {
                var storedRole = roleRepository.findByName(Roles.valueOf(role));
                storedRole.ifPresent(roles::add);
            });
        }
        var user = new User(command.email(), hashingService.encode(command.password()), roles);
        userRepository.save(user);
        return userRepository.findByEmail(command.email());
    }

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByEmail(command.email());
        if (user.isEmpty()) throw new RuntimeException("User not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");
        var token = tokenService.generateToken(user.get().getEmail());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    @Override
    public Long handle(ForgotPasswordCommand command) {
        var user = userRepository.findByEmail(command.email());
        if (user.isEmpty()) throw new RuntimeException("User not found");

        var existingToken = emailVerificationRepository
                .findFirstByEmailAndExpirationDateAfterAndVerifiedFalseOrderByCreatedAtDesc(
                        command.email(), LocalDateTime.now());

        if (existingToken.isPresent()) {
            throw new RuntimeException("Token not expired");
        }
        EmailVerification newEmailVerification = new EmailVerification(user.get().getEmail());
        emailVerificationRepository.save(newEmailVerification);
        emailService.sendPasswordResetEmail(command.email(), newEmailVerification.getVerificationToken() );
        return null;
    }

    @Override
    public Long handle(ResetPasswordCommand command) {

        emailVerificationRepository.findByToken(command.token()).map(emailVerification -> {
            emailVerification.verify(command.token());
            emailVerification.expireNow();
            emailVerificationRepository.save(emailVerification);
            return emailVerification.getId();
        }).orElseThrow(() -> new RuntimeException("Verification not found"));

        userRepository.findByEmail(command.email()).map(user -> {
            user.updatePassword(hashingService.encode(command.password()));
            userRepository.save(user);
            return user.getId();
        }).orElseThrow(() -> new RuntimeException("User not found"));
        return null;
    }

}
