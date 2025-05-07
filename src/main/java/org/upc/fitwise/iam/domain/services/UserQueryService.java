package org.upc.fitwise.iam.domain.services;

import org.upc.fitwise.iam.domain.model.aggregates.User;
import org.upc.fitwise.iam.domain.model.queries.GetAllUsersQuery;
import org.upc.fitwise.iam.domain.model.queries.GetUserByIdQuery;
import org.upc.fitwise.iam.domain.model.queries.GetUserByEmailQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByEmailQuery query);
}
