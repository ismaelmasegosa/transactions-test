package com.ismaelmasegosa.transaction.challenge.domain.core;

import java.util.List;

public class Error extends Validation {

  private final int statusCode;

  private final TransactionError transactionError;

  public Error(List<String> error, int statusCode) {
    super(error);
    this.statusCode = statusCode;
    this.transactionError = null;
  }

  public Error(List<String> error, int statusCode, TransactionError transactionError) {
    super(error);
    this.statusCode = statusCode;
    this.transactionError = transactionError;
  }

  public Error(int statusCode, TransactionError transactionError) {
    this.statusCode = statusCode;
    this.transactionError = transactionError;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public TransactionError getTransactionError() {
    return transactionError;
  }
}
