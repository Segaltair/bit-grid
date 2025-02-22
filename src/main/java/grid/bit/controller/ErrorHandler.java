package grid.bit.controller;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.Instant;
import java.util.Arrays;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response> handleIllegalArgumentException(
            IllegalArgumentException ex,
            ServletWebRequest request
    ) {
        var response = buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            ServletWebRequest request
    ) {
        var response = buildResponse(Arrays.toString(ex.getDetailMessageArguments()), HttpStatus.BAD_REQUEST, request);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Response> handleEntityNotFoundException(
            EntityNotFoundException ex,
            ServletWebRequest request
    ) {
        var response = buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(
            Exception ex,
            ServletWebRequest request
    ) {
        var response = buildResponse("unknown error", HttpStatus.INTERNAL_SERVER_ERROR, request);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Response buildResponse(String message, HttpStatus status, ServletWebRequest request) {
        return new Response(Instant.now(), status.value(), message, request.getRequest().getRequestURI());
    }

    public record Response(
            Instant timestamp,
            int status,
            String error,
            String path
    ) {
    }
}
