package org.upc.fitwise.iam.interfaces.rest.resources;

import java.util.List;

public record UserResource(Long id, String email, List<String> roles) {
}
