package org.upc.fitwise.iam.interfaces.rest.transform;

import org.upc.fitwise.iam.domain.model.commands.SignUpCommand;
import org.upc.fitwise.iam.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {

    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(resource.email(), resource.password(), resource.roles());
    }

}
