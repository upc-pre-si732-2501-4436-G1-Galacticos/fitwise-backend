package org.upc.fitwise.iam.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResetPasswordResource(@NotBlank @Email String email, @NotBlank String password,@NotBlank String verificationCode) {
}
