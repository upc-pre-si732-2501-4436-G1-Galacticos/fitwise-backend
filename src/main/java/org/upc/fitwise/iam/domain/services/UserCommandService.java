package org.upc.fitwise.iam.domain.services;

import org.upc.fitwise.iam.domain.model.aggregates.User;
import org.upc.fitwise.iam.domain.model.commands.*;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
    Optional<User> handle(SignUpCommand command);
    void handle(SignInCommand command);
    void handle(ForgotPasswordCommand command);
    void handle(ResetPasswordCommand command);
    Optional<ImmutablePair<User, String>> handle(VerifySignInCommand command);
}
