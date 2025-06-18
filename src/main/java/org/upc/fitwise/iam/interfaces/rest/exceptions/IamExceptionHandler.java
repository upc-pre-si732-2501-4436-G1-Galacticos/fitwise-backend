package org.upc.fitwise.iam.interfaces.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.upc.fitwise.iam.domain.exceptions.InvalidCredentialsException;
import org.upc.fitwise.iam.domain.exceptions.InvalidVerificationCodeException;
import org.upc.fitwise.iam.domain.exceptions.UserAlreadyExistsException;
import org.upc.fitwise.iam.domain.exceptions.UserNotFoundException;
import org.upc.fitwise.iam.domain.exceptions.VerificationCodeAlreadyActiveException;
import org.upc.fitwise.iam.domain.exceptions.VerificationCodeAlreadyUsedException; // Nueva importación
import org.upc.fitwise.iam.domain.exceptions.VerificationCodeExpiredException;   // Nueva importación
import org.upc.fitwise.shared.interfaces.rest.resources.MessageResource; // Asegúrate de que la ruta a MessageResource sea correcta

/**
 * Global exception handler for the 'iam' (Identity and Access Management) bounded context.
 * This class catches specific exceptions thrown by the IAM application and domain layers
 * and maps them to appropriate HTTP status codes and a standardized error response body.
 */
@RestControllerAdvice
public class IamExceptionHandler {

    /**
     * Handles UserAlreadyExistsException and returns HTTP 409 Conflict.
     * Used when trying to sign up with an email that is already registered.
     *
     * @param ex The exception thrown.
     * @return A MessageResource containing the error message.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public MessageResource handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return new MessageResource(ex.getMessage());
    }

    /**
     * Handles UserNotFoundException and returns HTTP 404 Not Found.
     * Used when a requested user resource does not exist.
     *
     * @param ex The exception thrown.
     * @return A MessageResource containing the error message.
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResource handleUserNotFoundException(UserNotFoundException ex) {
        return new MessageResource(ex.getMessage());
    }

    /**
     * Handles InvalidCredentialsException and returns HTTP 401 Unauthorized.
     * Used for failed login attempts due to incorrect email/password.
     *
     * @param ex The exception thrown.
     * @return A MessageResource containing the error message.
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public MessageResource handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return new MessageResource(ex.getMessage());
    }

    /**
     * Handles VerificationCodeAlreadyActiveException and returns HTTP 409 Conflict or 400 Bad Request.
     * Used when a new verification code is requested but one is already active.
     *
     * @param ex The exception thrown.
     * @return A MessageResource containing the error message.
     */
    @ExceptionHandler(VerificationCodeAlreadyActiveException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public MessageResource handleVerificationCodeAlreadyActiveException(VerificationCodeAlreadyActiveException ex) {
        return new MessageResource(ex.getMessage());
    }

    /**
     * Handles InvalidVerificationCodeException and returns HTTP 400 Bad Request.
     * Used when a provided verification code is incorrect or expired.
     *
     * @param ex The exception thrown.
     * @return A MessageResource containing the error message.
     */
    @ExceptionHandler(InvalidVerificationCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResource handleInvalidVerificationCodeException(InvalidVerificationCodeException ex) {
        return new MessageResource(ex.getMessage());
    }

    /**
     * Handles VerificationCodeAlreadyUsedException and returns HTTP 400 Bad Request or 410 Gone.
     * Used when a verification code has already been successfully consumed.
     *
     * @param ex The exception thrown.
     * @return A MessageResource containing the error message.
     */
    @ExceptionHandler(VerificationCodeAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResource handleVerificationCodeAlreadyUsedException(VerificationCodeAlreadyUsedException ex) {
        return new MessageResource(ex.getMessage());
    }

    /**
     * Handles VerificationCodeExpiredException and returns HTTP 400 Bad Request.
     * Used when a verification code has passed its expiration time.
     *
     * @param ex The exception thrown.
     * @return A MessageResource containing the error message.
     */
    @ExceptionHandler(VerificationCodeExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResource handleVerificationCodeExpiredException(VerificationCodeExpiredException ex) {
        return new MessageResource(ex.getMessage());
    }




}