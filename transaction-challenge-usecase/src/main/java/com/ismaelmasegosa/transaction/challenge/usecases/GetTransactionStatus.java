package com.ismaelmasegosa.transaction.challenge.usecases;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCase;
import com.ismaelmasegosa.transaction.challenge.usecases.params.GetTransactionStatusParams;

public class GetTransactionStatus implements UseCase<GetTransactionStatusParams, Either<Error, Transaction>> {

  @Override
  public Either<Error, Transaction> execute(GetTransactionStatusParams params) {
    throw new UnsupportedOperationException("Not implemented");
  }
}
