package org.upc.fitwise.iam.application.internal.commandservices;

import org.upc.fitwise.iam.application.internal.outboundservices.email.EmailService;
import org.upc.fitwise.iam.application.internal.outboundservices.hashing.HashingService;
import org.upc.fitwise.iam.application.internal.outboundservices.tokens.TokenService;
import org.upc.fitwise.iam.domain.exceptions.*;
import org.upc.fitwise.iam.domain.model.aggregates.EmailVerification;
import org.upc.fitwise.iam.domain.model.aggregates.User;
import org.upc.fitwise.iam.domain.model.commands.*;
import org.upc.fitwise.iam.domain.model.entities.Role;
import org.upc.fitwise.iam.domain.model.valueobjects.Code;
import org.upc.fitwise.iam.domain.model.valueobjects.Roles;
import org.upc.fitwise.iam.domain.model.valueobjects.VerificationType;
import org.upc.fitwise.iam.domain.services.UserCommandService;
import org.upc.fitwise.iam.infrastructure.persistence.jpa.repositories.EmailVerificationRepository;
import org.upc.fitwise.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.upc.fitwise.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

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

    private boolean hasPendingVerificationCode(String email, VerificationType type) {
        return emailVerificationRepository
                .findFirstByEmailAndVerifiedFalseAndVerificationTypeOrderByCreatedAtDesc(email, type)
                .filter(token->!token.isExpired())
                .isPresent();
    }

    private EmailVerification validateVerificationCodeOrThrow(Code code, VerificationType type) {
        var verificationCode = emailVerificationRepository
                .findFirstByVerificationCodeAndVerifiedFalseAndVerificationTypeOrderByCreatedAtDesc(code, type)
                .orElseThrow(InvalidVerificationCodeException::new);
        if(verificationCode.isCodeInvalid(code)) throw new VerificationCodeAlreadyUsedException();
        if(verificationCode.isExpired()) throw  new  VerificationCodeExpiredException();
        if(verificationCode.isVerified()) throw new VerificationCodeAlreadyUsedException();
        verificationCode.setVerified(true);
        verificationCode.expireNow();
        return verificationCode;
    }



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
            throw new UserAlreadyExistsException(command.email());

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
    public void  handle(SignInCommand command) {
        var user = userRepository.findByEmail(command.email());
        if (user.isEmpty()) throw new UserNotFoundException(command.email());
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new InvalidCredentialsException();

        if (hasPendingVerificationCode(command.email(), VerificationType.SIGN_IN)) {
            throw new VerificationCodeAlreadyActiveException();
        }

        EmailVerification newEmailVerification = new EmailVerification(user.get().getEmail(), VerificationType.SIGN_IN);
        emailVerificationRepository.save(newEmailVerification);

        emailService.sendCodeToEmail(command.email(), newEmailVerification.getVerificationCode().verificationCode(),VerificationType.SIGN_IN);
    }

    public Optional<ImmutablePair<User, String>> handle(VerifySignInCommand command) {
        var code =new Code(command.verificationCode());
        var user = userRepository.findByEmail(command.email());
        if (user.isEmpty()) {
            throw new UserNotFoundException(command.email());
        }

        var updateStatusVerification = validateVerificationCodeOrThrow(code, VerificationType.SIGN_IN);
        emailVerificationRepository.save(updateStatusVerification);



        var token = tokenService.generateToken(user.get().getEmail());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }



    @Override
    public void handle(ForgotPasswordCommand command) {
        var user = userRepository.findByEmail(command.email());
        if (user.isEmpty()) throw new UserNotFoundException(command.email());

        if (hasPendingVerificationCode(command.email(), VerificationType.PASSWORD_RESET)) {
            throw new VerificationCodeAlreadyActiveException();
        }

        EmailVerification newEmailVerification = new EmailVerification(user.get().getEmail(), VerificationType.PASSWORD_RESET);
        emailVerificationRepository.save(newEmailVerification);

        emailService.sendCodeToEmail(command.email(), newEmailVerification.getVerificationCode().verificationCode(),VerificationType.PASSWORD_RESET);

    }

    @Override
    public void handle(ResetPasswordCommand command) {
         var code=new Code(command.token());

        var updateStatusVerification = validateVerificationCodeOrThrow(code, VerificationType.PASSWORD_RESET);
        emailVerificationRepository.save(updateStatusVerification);

        userRepository.findByEmail(command.email()).map(user -> {
            user.updatePassword(hashingService.encode(command.password()));
            userRepository.save(user);
            return user.getId();
        }).orElseThrow(() -> new UserNotFoundException(command.email()));
    }



}
