package org.upc.fitwise.iam.domain.model.commands;

public record VerifySignInCommand(String email, String verificationCode) {
}
