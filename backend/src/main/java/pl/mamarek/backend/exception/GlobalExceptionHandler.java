package pl.mamarek.backend.exception;

import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // ===================================================================================
    // 1. CUSTOM EXCEPTIONS (Keep these: Spring doesn't know about them)
    // ===================================================================================

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage(),
                LocalDateTime.now(), request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), "Bad Request", ex.getMessage(),
                LocalDateTime.now(), request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(), "Conflict", ex.getMessage(),
                LocalDateTime.now(), request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    // ===================================================================================
    // 2. SECURITY & STANDARD JAVA EXCEPTIONS (Keep these too)
    // ===================================================================================

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(), "Unauthorized", ex.getMessage(),
                LocalDateTime.now(), request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(), "Forbidden", ex.getMessage(),
                LocalDateTime.now(), request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), "Bad Request",
                ex.getMessage() != null ? ex.getMessage() : "Invalid argument provided",
                LocalDateTime.now(), request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // ===================================================================================
    // 3. OVERRIDDEN SPRING EXCEPTIONS (This fixes your crash!)
    // ===================================================================================

    // Replaced @ExceptionHandler with @Override so it doesn't conflict with the parent class
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Validation failed");

        ErrorResponse errorResponse = new ErrorResponse(
                status.value(), "Bad Request", message,
                LocalDateTime.now(), request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(errorResponse, headers, status);
    }

    // (BONUS) Overriding the parent's master method ensures that ALL other Spring MVC errors
    // (like 405 Method Not Allowed) are returned using your custom ErrorResponse format!
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, @NonNull HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                statusCode.value(), "Spring MVC Error", ex.getMessage(),
                LocalDateTime.now(), request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(errorResponse, headers, statusCode);
    }

    // ===================================================================================
    // 4. GLOBAL CATCH-ALL FOR 500 ERRORS (e.g. NullPointerException, DB crashes)
    // ===================================================================================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error",
                "An unexpected error occurred. Please try again later.",
                LocalDateTime.now(), request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}