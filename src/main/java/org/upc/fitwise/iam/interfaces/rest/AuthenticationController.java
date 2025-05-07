package org.upc.fitwise.iam.interfaces.rest;

import jakarta.validation.Valid;
import org.upc.fitwise.iam.domain.model.commands.ForgotPasswordCommand;
import org.upc.fitwise.iam.domain.model.commands.ResetPasswordCommand;
import org.upc.fitwise.iam.domain.services.UserCommandService;
import org.upc.fitwise.iam.interfaces.rest.resources.*;
import org.upc.fitwise.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import org.upc.fitwise.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import org.upc.fitwise.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import org.upc.fitwise.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upc.fitwise.shared.interfaces.rest.resources.MessageResource;

/**
 * AuthenticationController
 * <p>
 *     This controller is responsible for handling authentication requests.
 *     It exposes two endpoints:
 *     <ul>
 *         <li>POST /api/v1/authentication/sign-in</li>
 *         <li>POST /api/v1/authentication/sign-up</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {
    private final UserCommandService userCommandService;

    public AuthenticationController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    /**
     * Handles the sign-in request.
     * @param signInResource the sign-in request body.
     * @return the authenticated user resource.
     */
    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticatedUserResource> signIn(@Valid @RequestBody SignInResource signInResource) {
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
        var authenticatedUser = userCommandService.handle(signInCommand);
        if (authenticatedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(authenticatedUser.get().getLeft(), authenticatedUser.get().getRight());
        return ResponseEntity.ok(authenticatedUserResource);
    }

    /**
     * Handles the sign-up request.
     * @param signUpResource the sign-up request body.
     * @return the created user resource.
     */
    @PostMapping("/sign-up")
    public ResponseEntity<UserResource> signUp(@Valid @RequestBody SignUpResource signUpResource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
        var user = userCommandService.handle(signUpCommand);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }

    /**
     * Handles the forgot-password request.
     * @param forgotPasswordResource the forgot-password request body.
     * @return message.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResource>forgotPassword(@Valid @RequestBody ForgotPasswordResource forgotPasswordResource) {
        var forgotPasswordCommand = new ForgotPasswordCommand(forgotPasswordResource.email());
        userCommandService.handle(forgotPasswordCommand);
        return ResponseEntity.ok(new MessageResource("Instrucciones enviadas al correo siguiente " + forgotPasswordResource.email()));
    }
    /**
     * Handles the reset-password request.
     * @param resetPasswordResource the reset-password request body.
     * @return message.
     */
    @PostMapping("/reset-password")
    public ResponseEntity<MessageResource> resetPassword(@Valid @RequestBody ResetPasswordResource resetPasswordResource) {
        var resetPasswordCommand = new ResetPasswordCommand(resetPasswordResource.email(), resetPasswordResource.password(),resetPasswordResource.token());
        userCommandService.handle(resetPasswordCommand);
        return ResponseEntity.ok(new MessageResource("Contraseña actualizada del correo " + resetPasswordResource.email()));

    }
}
