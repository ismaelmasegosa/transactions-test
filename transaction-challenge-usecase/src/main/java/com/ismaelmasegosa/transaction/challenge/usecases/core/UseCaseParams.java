package com.ismaelmasegosa.transaction.challenge.usecases.core;

import static com.ismaelmasegosa.transaction.challenge.domain.core.Validation.empty;

import com.ismaelmasegosa.transaction.challenge.domain.core.Validation;

public interface UseCaseParams {

  default Validation validate() {
    return empty();
  }

}
