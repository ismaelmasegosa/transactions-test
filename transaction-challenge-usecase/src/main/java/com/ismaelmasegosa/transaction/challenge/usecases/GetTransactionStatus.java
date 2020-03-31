package com.ismaelmasegosa.transaction.challenge.usecases;

import static com.ismaelmasegosa.transaction.challenge.domain.core.Either.left;
import static com.ismaelmasegosa.transaction.challenge.domain.core.Either.right;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.core.Validation;
import com.ismaelmasegosa.transaction.challenge.domain.core.error.BadRequestError;
import com.ismaelmasegosa.transaction.challenge.domain.core.error.TransactionNotFoundError;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.TransactionStatus;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.TransactionsStatusProvider;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCase;
import com.ismaelmasegosa.transaction.challenge.usecases.params.GetTransactionStatusParams;
import java.util.List;
import java.util.Optional;

public class GetTransactionStatus implements UseCase<GetTransactionStatusParams, Either<Error, TransactionStatus>> {

  private final TransactionCollection transactionCollection;

  private final TransactionsStatusProvider transactionsStatusProvider;

  public GetTransactionStatus(TransactionCollection transactionCollection) {
    this.transactionCollection = transactionCollection;
    this.transactionsStatusProvider = new TransactionsStatusProvider();
  }

  @Override
  public Either<Error, TransactionStatus> execute(GetTransactionStatusParams params) {
    final Validation validationError = params.validate();
    if (validationError.hasErrors()) {
      return left(createBadRequestResponse(validationError.getErrors()));
    } else {
      Optional<Transaction> optionalTransaction = transactionCollection.findByReference(params.getReference());
      if (optionalTransaction.isPresent()) {
        Transaction transaction = optionalTransaction.get();
        return right(transactionsStatusProvider.getTransactionStatus(transaction, params.getChannel()));
      } else {
        return left(new TransactionNotFoundError(params.getReference()));
      }
    }
  }

  private BadRequestError createBadRequestResponse(List<String> errors) {
    return new BadRequestError(errors);
  }

}
