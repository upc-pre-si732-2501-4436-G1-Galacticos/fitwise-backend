package org.upc.fitwise.iam.domain.services;

import org.upc.fitwise.iam.domain.model.entities.Role;
import org.upc.fitwise.iam.domain.model.queries.GetAllRolesQuery;
import org.upc.fitwise.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

public interface RoleQueryService {
    List<Role> handle(GetAllRolesQuery query);
    Optional<Role> handle(GetRoleByNameQuery query);
}
