package com.ismaelmasegosa.transaction.challenge.domain.core.error;

import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import java.util.List;

public class ConflictError extends Error {

  private final static int STATUS_CODE = 409;

  public ConflictError(List<String> error) {
    super(error, STATUS_CODE);
  }
}
