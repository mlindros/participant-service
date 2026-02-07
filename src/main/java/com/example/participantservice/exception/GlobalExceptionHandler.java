package com.example.participantservice.exception;

import com.example.participantservice.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static com.example.participantservice.domain.constant.AppConstants.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EnrollmentException.class)
    public ResponseEntity<ErrorResponse> handleEnrollmentException(EnrollmentException ex) {
        var status = switch(ex.getMessage()) {
            case RECORD_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case ALREADY_ENROLLED -> HttpStatus.CONFLICT;
            case INELIGIBLE_AGE -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        var error = new ErrorResponse(
                ex.getMessage(),
                "Business rule violation occurred during enrollment",
                LocalDateTime.now()
        );

        return ResponseEntity.status(status).body(error);
    }
}
