package com.ismaelmasegosa.transaction.challenge.infrastructure.rest.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

  private int statusCode;

  private String errorType;

  private long timestamp;

  private List<String> errors;
}
