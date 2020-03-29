package com.ismaelmasegosa.transaction.challenge.usecases;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.events.DomainEvent;
import com.ismaelmasegosa.transaction.challenge.domain.events.DomainEventPublisher;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.usecases.params.CreateTransactionParams;
import java.util.List;
import org.junit.Test;

public class CreateTransactionTest {

  DomainEventPublisher eventPublisher = mock(DomainEventPublisher.class);

  TransactionCollection transactionCollection = mock(TransactionCollection.class);

  CreateTransaction createTransaction = new CreateTransaction(transactionCollection);

  @Test
  public void given_A_Transaction_When_The_Create_Transaction_Use_Case_Is_Executed_Then_The_Created_Transacton_Should_Be_Returned() {
    // given
    long date = System.currentTimeMillis();
    String reference = "12345A";
    String accountIban = "ES9820385778983000760236";
    double amount = 193.38;
    double fee = 3.18;
    String description = "Restaurant payment";
    CreateTransactionParams params = new CreateTransactionParams(reference, accountIban, date, amount, fee, description);
    Transaction transaction = new Transaction(reference, accountIban, date, amount, fee, description);
    given(transactionCollection.addTransaction(any(Transaction.class))).willReturn(transaction);

    // when
    Either<Error, Transaction> response = createTransaction.execute(params);

    // then
    verify(eventPublisher, times(1)).publish(any(DomainEvent.class));
    assertTrue(response.isRight());
    Transaction transactionResponse = response.get();
    assertEquals(reference, transactionResponse.getReference());
    assertEquals(accountIban, transactionResponse.getAccountIban());
    assertEquals(amount, transactionResponse.getAmount(), Double.POSITIVE_INFINITY);
    assertEquals(fee, transactionResponse.getFee(), Double.POSITIVE_INFINITY);
    assertEquals(date, transactionResponse.getDate());
    assertEquals(description, transactionResponse.getDescription());
  }

  @Test
  public void given_A_Invalid_Transaction_Fields_When_The_Create_Transaction_Use_Case_Is_Executed_Then_Validation_Error_Should_Be_Returned() {
    // given
    List<String> errors = asList("Account IBAN can not be empty", "Amount can not be zero", "Fee must be greater that zero");
    long date = System.currentTimeMillis();
    final String reference = "12345A";
    final String accountIban = "";
    final double amount = 0.0;
    final double fee = -3.18;
    final String description = "Restaurant payment";
    CreateTransactionParams params = new CreateTransactionParams(reference, accountIban, date, amount, fee, description);
    Transaction transaction = new Transaction(reference, accountIban, date, amount, fee, description);
    given(transactionCollection.addTransaction(any(Transaction.class))).willReturn(transaction);

    // when
    Either<Error, Transaction> response = createTransaction.execute(params);

    // then
    assertTrue(response.isLeft());
    Error errorValidation = response.getLeft();
    assertEquals(400, errorValidation.getStatusCode());
    assertEquals(errors, errorValidation.getErrors());
  }
}
