package com.ismaelmasegosa.transaction.challenge.usecases;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.util.StringUtils.isEmpty;

import com.ismaelmasegosa.transaction.challenge.domain.account.AccountBalanceProvider;
import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.events.DomainEvent;
import com.ismaelmasegosa.transaction.challenge.domain.events.DomainEventPublisher;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.usecases.params.CreateTransactionParams;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CreateTransactionTest {

  DomainEventPublisher eventPublisher = mock(DomainEventPublisher.class);

  TransactionCollection transactionCollection = mock(TransactionCollection.class);

  AccountBalanceProvider accountBalanceProvider = mock(AccountBalanceProvider.class);

  CreateTransaction createTransaction = new CreateTransaction(eventPublisher, accountBalanceProvider, transactionCollection);

  @Before
  public void setUp() {
    given(accountBalanceProvider.getAccountBalance(anyString())).willReturn(678.89);
  }

  @Test
  public void given_A_Transaction_When_The_Create_Transaction_Use_Case_Is_Executed_Then_The_Created_Transaction_Should_Be_Returned() {
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

  @Test
  public void given_A_Transaction_With_Negative_Amount_Greater_That_Balance_Account_When_The_Create_Transaction_Use_Case_Is_Executed_Then_Forbidden_Error_Should_Be_Returned() {
    // given
    List<String> errors = singletonList("Transaction not created, the total account balance can not be bellow zero");
    long date = System.currentTimeMillis();
    String reference = "12345A";
    String accountIban = "ES9820385778983000760236";
    final double amount = -1678.87;
    final double fee = 3.18;
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

  @Test
  public void given_A_Transaction_Without_Reference_When_The_Create_Transaction_Use_Case_Is_Executed_Then_The_Created_Transaction_With_Reference_Should_Be_Returned() {
    // given
    List<String> errors = singletonList("Transaction not created, the total account balance can not be bellow zero");
    long date = System.currentTimeMillis();
    String reference = null;
    String accountIban = "ES9820385778983000760236";
    final double amount = 1678.87;
    final double fee = 3.18;
    final String description = "Restaurant payment";
    CreateTransactionParams params = new CreateTransactionParams(reference, accountIban, date, amount, fee, description);
    Transaction transaction = new Transaction(reference, accountIban, date, amount, fee, description);
    given(transactionCollection.addTransaction(any(Transaction.class))).willReturn(transaction);

    // when
    Either<Error, Transaction> response = createTransaction.execute(params);

    // then
    assertTrue(response.isRight());
    Transaction transactionResponse = response.get();
    assertFalse(isEmpty(transactionResponse.getReference()));
  }
}
