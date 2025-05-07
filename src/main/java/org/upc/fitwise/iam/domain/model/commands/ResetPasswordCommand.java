package org.upc.fitwise.iam.domain.model.commands;

public record ResetPasswordCommand(String email, String password,String token) {
}
