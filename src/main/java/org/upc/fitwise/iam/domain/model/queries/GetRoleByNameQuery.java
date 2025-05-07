package org.upc.fitwise.iam.domain.model.queries;

import org.upc.fitwise.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles roleName) {
}
