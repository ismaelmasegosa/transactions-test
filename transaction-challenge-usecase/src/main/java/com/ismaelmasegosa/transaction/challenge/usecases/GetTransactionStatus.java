package com.ismaelmasegosa.transaction.challenge.usecases;

import static com.ismaelmasegosa.transaction.challenge.domain.core.Either.left;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.core.error.TransactionNotFoundError;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.TransactionStatus;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCase;
import com.ismaelmasegosa.transaction.challenge.usecases.params.GetTransactionStatusParams;

public class GetTransactionStatus implements UseCase<GetTransactionStatusParams, Either<Error, TransactionStatus>> {

  private final TransactionCollection transactionCollection;

  public GetTransactionStatus(TransactionCollection transactionCollection) {
    this.transactionCollection = transactionCollection;
  }

  @Override
  public Either<Error, TransactionStatus> execute(GetTransactionStatusParams params) {
    if (!transactionCollection.findByReference(params.getReference()).isPresent()) {
      return left(new TransactionNotFoundError(params.getReference()));
    } else {
      throw new UnsupportedOperationException("Not implemented");
    }
  }
}
