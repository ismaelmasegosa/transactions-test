package com.ismaelmasegosa.transaction.challenge.domain.core;

import java.util.List;

public class Error extends Validation {

  private final int statusCode;

  public Error(List<String> error, int statusCode) {
    super(error);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }

}
