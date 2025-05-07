package org.upc.fitwise.iam.interfaces.rest.transform;

import org.upc.fitwise.iam.domain.model.commands.SignInCommand;
import org.upc.fitwise.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.email(), signInResource.password());
    }
}
