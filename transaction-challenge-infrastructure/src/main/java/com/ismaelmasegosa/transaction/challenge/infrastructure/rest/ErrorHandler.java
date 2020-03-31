package com.ismaelmasegosa.transaction.challenge.infrastructure.rest;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.resolve;
import static org.springframework.http.HttpStatus.valueOf;

import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.infrastructure.rest.dto.ErrorResponse;
import java.time.Instant;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ErrorHandler {

  public static ResponseEntity<?> returnResponseEntity(Error error) {
    if (resolve(error.getStatusCode()) != null) {
      if (error.getTransactionError() == null) {
        ErrorResponse errorResponse = createErrorResponse(error.getStatusCode(), error.getErrors());
        return ResponseEntity.status(valueOf(error.getStatusCode())).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
      } else {
        return ResponseEntity.status(valueOf(error.getStatusCode())).contentType(MediaType.APPLICATION_JSON)
            .body(error.getTransactionError());
      }
    } else {
      ErrorResponse errorResponse = createErrorResponse(500, singletonList("Internal server error"));
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }
  }

  private static ErrorResponse createErrorResponse(int statusCode, List<String> errors) {
    return new ErrorResponse(statusCode, valueOf(statusCode).name(), Instant.now().toEpochMilli(), errors);
  }

}
