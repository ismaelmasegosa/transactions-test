package com.ismaelmasegosa.transaction.challenge.domain.core.error;

import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import java.util.List;

public class BadRequestError extends Error {

  private final static int STATUS_CODE = 400;

  public BadRequestError(List<String> error) {
    super(error, STATUS_CODE);
  }
}
