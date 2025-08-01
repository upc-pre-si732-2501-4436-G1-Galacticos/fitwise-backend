package org.upc.fitwise.iam.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifySignInResource(@NotBlank @Email String email, @NotBlank String verificationCode) {
}
