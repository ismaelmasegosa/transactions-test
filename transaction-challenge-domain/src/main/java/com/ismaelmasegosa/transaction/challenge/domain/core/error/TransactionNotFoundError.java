package com.ismaelmasegosa.transaction.challenge.domain.core.error;

import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.core.TransactionError;

public class TransactionNotFoundError extends Error {

  private final static int STATUS_CODE = 404;

  public TransactionNotFoundError(String reference) {
    super(STATUS_CODE, new TransactionError(reference));
  }
}
