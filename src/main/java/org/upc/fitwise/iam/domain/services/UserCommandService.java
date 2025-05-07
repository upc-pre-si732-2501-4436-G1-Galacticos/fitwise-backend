package org.upc.fitwise.iam.domain.services;

import org.upc.fitwise.iam.domain.model.aggregates.User;
import org.upc.fitwise.iam.domain.model.commands.ForgotPasswordCommand;
import org.upc.fitwise.iam.domain.model.commands.ResetPasswordCommand;
import org.upc.fitwise.iam.domain.model.commands.SignInCommand;
import org.upc.fitwise.iam.domain.model.commands.SignUpCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
    Optional<User> handle(SignUpCommand command);
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);
    Long handle(ForgotPasswordCommand command);
    Long handle(ResetPasswordCommand command);
}
