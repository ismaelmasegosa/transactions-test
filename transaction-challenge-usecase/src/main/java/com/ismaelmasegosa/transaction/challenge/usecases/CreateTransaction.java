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
import com.ismaelmasegosa.transaction.challenge.domain.core.error.ConflictError;
import com.ismaelmasegosa.transaction.challenge.domain.events.DomainEvent;
import com.ismaelmasegosa.transaction.challenge.domain.events.DomainEventPublisher;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCase;
import com.ismaelmasegosa.transaction.challenge.usecases.params.CreateTransactionParams;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class CreateTransaction implements UseCase<CreateTransactionParams, Either<Error, Transaction>> {

  private static final String HAS_NOT_AVAILABLE_BALANCE = "Transaction not created, the total account balance can not be bellow zero";

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
      return left(createBadRequestResponse(validationError.getErrors()));
    } else {
      Optional<Transaction> optionalTransaction = transactionCollection.findByReference(params.getReference());
      return optionalTransaction.<Either<Error, Transaction>>map(transaction -> left(createConflictErrorResponse()))
          .orElseGet(() -> createTransactionAction(params));
    }
  }

  private Function<CreateTransactionParams, Transaction> mapAsDomainTransaction() {
    return params -> new Transaction(params.getReference(), params.getAccountIban(), params.getDate(), params.getAmount(), params.getFee(),
        params.getDescription());
  }

  private Either<Error, Transaction> createTransactionAction(CreateTransactionParams params) {
    if (hasAvailableAccountBalance(params)) {
      return right(mapAsDomainTransaction().andThen(addTransaction()).andThen(publishEvent()).apply(params));
    } else {
      return left(createBadRequestResponse(singletonList(HAS_NOT_AVAILABLE_BALANCE)));
    }
  }

  private UnaryOperator<Transaction> addTransaction() {
    return transactionCollection::addTransaction;
  }

  private UnaryOperator<Transaction> publishEvent() {
    return transaction -> {
      eventPublisher.publish(asDomainEvent(transaction));
      return transaction;
    };
  }

  private DomainEvent asDomainEvent(Transaction transaction) {
    double totalAmount = calculateTotalAmount(transaction.getAmount(), transaction.getFee());
    return new UpdateAccountBalanceEvent(transaction.getAccountIban(), totalAmount);
  }

  private boolean hasAvailableAccountBalance(CreateTransactionParams params) {
    double accountBalance = accountBalanceProvider.getAccountBalance(params.getAccountIban());
    double totalAmount = calculateTotalAmount(params.getAmount(), params.getFee());
    double finalAccountBalance = accountBalance + totalAmount;
    return finalAccountBalance > 0;
  }

  private BadRequestError createBadRequestResponse(List<String> errors) {
    return new BadRequestError(errors);
  }

  private ConflictError createConflictErrorResponse() {
    return new ConflictError(singletonList("Al ready exist a transaction with same reference"));
  }

  private double calculateTotalAmount(double amount, double fee) {
    return amount - fee;
  }
}
