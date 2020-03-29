package com.ismaelmasegosa.transaction.challenge.usecases;

import static com.ismaelmasegosa.transaction.challenge.domain.core.Either.left;
import static com.ismaelmasegosa.transaction.challenge.domain.core.Either.right;
import static java.util.Collections.singletonList;

import com.ismaelmasegosa.transaction.challenge.domain.account.AccountBalanceProvider;
import com.ismaelmasegosa.transaction.challenge.domain.account.events.UpdateAccountBalanceEvent;
import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.core.Validation;
import com.ismaelmasegosa.transaction.challenge.domain.core.error.BadRequestError;
import com.ismaelmasegosa.transaction.challenge.domain.events.DomainEventPublisher;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCase;
import com.ismaelmasegosa.transaction.challenge.usecases.params.CreateTransactionParams;
import java.util.function.Function;

public class CreateTransaction implements UseCase<CreateTransactionParams, Either<Error, Transaction>> {

  private final DomainEventPublisher eventPublisher;

  private final AccountBalanceProvider accountBalanceProvider;

  private final TransactionCollection transactionCollection;

  public CreateTransaction(DomainEventPublisher eventPublisher, AccountBalanceProvider accountBalanceProvider,
      TransactionCollection transactionCollection) {
    this.eventPublisher = eventPublisher;
    this.accountBalanceProvider = accountBalanceProvider;
    this.transactionCollection = transactionCollection;
  }

  @Override
  public Either<Error, Transaction> execute(CreateTransactionParams params) {
    final Validation validationError = params.validate();
    if (validationError.hasErrors()) {
      BadRequestError badRequestError = new BadRequestError(validationError.getErrors());
      return left(badRequestError);
    } else {
      double accountBalance = accountBalanceProvider.getAccountBalance(params.getAccountIban());
      double totalAmount = params.getAmount() - params.getFee();
      double finalAccountBalance = accountBalance + totalAmount;
      if (finalAccountBalance < 0) {
        return left(new BadRequestError(singletonList("Transaction not created, the total account balance can not be bellow zero")));
      }
      Transaction transactionSaved = mapAsDomainTransaction().andThen(addTransaction()).apply(params);
      eventPublisher.publish(mapToDomainEvent(params, totalAmount));
      return right(transactionSaved);
    }
  }

  private UpdateAccountBalanceEvent mapToDomainEvent(CreateTransactionParams params, double totalAmount) {
    return new UpdateAccountBalanceEvent(params.getAccountIban(), totalAmount);
  }

  private Function<CreateTransactionParams, Transaction> mapAsDomainTransaction() {
    return this::asDomainTransaction;
  }

  public Transaction asDomainTransaction(CreateTransactionParams params) {
    return new Transaction(params.getReference(), params.getAccountIban(), params.getDate(), params.getAmount(), params.getFee(),
        params.getDescription());
  }

  public Function<Transaction, Transaction> addTransaction() {
    return transactionCollection::addTransaction;
  }
}
