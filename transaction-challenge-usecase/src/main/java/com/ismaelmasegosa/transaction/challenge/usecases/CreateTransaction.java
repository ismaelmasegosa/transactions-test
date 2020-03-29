package com.ismaelmasegosa.transaction.challenge.usecases;

import static com.ismaelmasegosa.transaction.challenge.domain.core.Either.right;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCase;
import com.ismaelmasegosa.transaction.challenge.usecases.params.CreateTransactionParams;
import java.util.function.Function;

public class CreateTransaction implements UseCase<CreateTransactionParams, Either<Error, Transaction>> {

  private final TransactionCollection accountCollection;

  public CreateTransaction(TransactionCollection accountCollection) {
    this.accountCollection = accountCollection;
  }

  @Override
  public Either<Error, Transaction> execute(CreateTransactionParams params) {
    Transaction transactionSaved = accountCollection.addTransaction(mapAsDomainTransaction().apply(params));
    return right(transactionSaved);
  }

  private Function<CreateTransactionParams, Transaction> mapAsDomainTransaction() {
    return this::asDomainTransaction;
  }

  public Transaction asDomainTransaction(CreateTransactionParams params) {
    return new Transaction(params.getReference(), params.getAccountIban(), params.getDate(), params.getAmount(), params.getFee(),
        params.getDescription());
  }
}
