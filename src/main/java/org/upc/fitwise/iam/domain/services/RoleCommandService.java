package org.upc.fitwise.iam.domain.services;

import org.upc.fitwise.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
