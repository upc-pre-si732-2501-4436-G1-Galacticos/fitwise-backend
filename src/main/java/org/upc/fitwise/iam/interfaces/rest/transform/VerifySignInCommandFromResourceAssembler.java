package org.upc.fitwise.iam.interfaces.rest.transform;

import org.upc.fitwise.iam.domain.model.commands.VerifySignInCommand;
import org.upc.fitwise.iam.interfaces.rest.resources.VerifySignInResource;


public class VerifySignInCommandFromResourceAssembler {
    public static VerifySignInCommand toCommandFromResource(VerifySignInResource verifySignInResource) {
        return new VerifySignInCommand(verifySignInResource.email(), verifySignInResource.verificationCode());
    }
}
