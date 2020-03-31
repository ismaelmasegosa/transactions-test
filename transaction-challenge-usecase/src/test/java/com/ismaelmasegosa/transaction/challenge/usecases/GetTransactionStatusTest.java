package com.ismaelmasegosa.transaction.challenge.usecases;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.core.TransactionError;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.TransactionStatus;
import com.ismaelmasegosa.transaction.challenge.usecases.params.GetTransactionStatusParams;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.Test;

public class GetTransactionStatusTest {

  TransactionCollection transactionCollection = mock(TransactionCollection.class);

  GetTransactionStatus getTransactionStatus = new GetTransactionStatus(transactionCollection);

  @Test
  public void given_A_Invalid_Reference_When_The_Get_Transaction_Status_Use_Case_Is_Executed_Then_The_Transactions_Error_Should_Be_Returned() {
    // given
    String channel = "INTERNAL";
    String reference = "211223A";
    GetTransactionStatusParams params = new GetTransactionStatusParams(reference, channel);
    given(transactionCollection.findByReference(reference)).willReturn(empty());

    // when
    Either<Error, TransactionStatus> response = getTransactionStatus.execute(params);

    // then
    assertTrue(response.isLeft());
    Error error = response.getLeft();
    TransactionError transactionError = error.getTransactionError();
    assertEquals(404, error.getStatusCode());
    assertEquals(reference, transactionError.getReference());
    assertEquals("INVALID", transactionError.getStatus());
  }

  @Test
  public void given_A_Reference_And_Client_And_Before_Today_Date_Channel_When_The_Get_Transaction_Status_Use_Case_Is_Executed_Then_The_Transaction_Should_Be_Returned() {
    // given
    String channel = "CLIENT";
    String reference = "11111A";
    long date = LocalDateTime.now().minusDays(2).toInstant(ZoneOffset.UTC).toEpochMilli();
    double amount = 120.98;
    double fee = 2.00;
    String accountIban = "ES9820385778983000760236";
    String description = "Restaurant payment";
    String status = "SETTLED";
    GetTransactionStatusParams params = new GetTransactionStatusParams(reference, channel);
    Transaction transaction = new Transaction(reference, accountIban, date, amount, fee, description);
    given(transactionCollection.findByReference(reference)).willReturn(of(transaction));

    // when
    Either<Error, TransactionStatus> response = getTransactionStatus.execute(params);

    // then
    double expectedAmount = amount - fee;
    assertTrue(response.isRight());
    TransactionStatus transactionStatusResponse = response.get();
    assertEquals(reference, transactionStatusResponse.getReference());
    assertEquals(status, transactionStatusResponse.getStatus());
    assertEquals(expectedAmount, transactionStatusResponse.getAmount(), Double.NaN);
  }

  @Test
  public void given_A_Reference_And_ATM_Channel_And_Before_Today_Date_When_The_Get_Transaction_Status_Use_Case_Is_Executed_Then_The_Transaction_Should_Be_Returned() {
    // given
    String channel = "ATM";
    String reference = "11111A";
    long date = LocalDateTime.now().minusDays(2).toInstant(ZoneOffset.UTC).toEpochMilli();
    double amount = 120.98;
    double fee = 2.00;
    String accountIban = "ES9820385778983000760236";
    String description = "Restaurant payment";
    String status = "SETTLED";
    GetTransactionStatusParams params = new GetTransactionStatusParams(reference, channel);
    Transaction transaction = new Transaction(reference, accountIban, date, amount, fee, description);
    given(transactionCollection.findByReference(reference)).willReturn(of(transaction));

    // when
    Either<Error, TransactionStatus> response = getTransactionStatus.execute(params);

    // then
    double expectedAmount = amount - fee;
    assertTrue(response.isRight());
    TransactionStatus transactionStatusResponse = response.get();
    assertEquals(reference, transactionStatusResponse.getReference());
    assertEquals(status, transactionStatusResponse.getStatus());
    assertEquals(expectedAmount, transactionStatusResponse.getAmount(), Double.NaN);
  }

  @Test
  public void given_A_Reference_And_Internal_And_Before_Today_Date_Channel_When_The_Get_Transaction_Status_Use_Case_Is_Executed_Then_The_Transaction_Should_Be_Returned() {
    // given
    String channel = "INTERNAL";
    String reference = "11111A";
    long date = LocalDateTime.now().minusDays(2).toInstant(ZoneOffset.UTC).toEpochMilli();
    double amount = 120.98;
    double fee = 2.00;
    String accountIban = "ES9820385778983000760236";
    String description = "Restaurant payment";
    String status = "SETTLED";
    GetTransactionStatusParams params = new GetTransactionStatusParams(reference, channel);
    Transaction transaction = new Transaction(reference, accountIban, date, amount, fee, description);
    given(transactionCollection.findByReference(reference)).willReturn(of(transaction));

    // when
    Either<Error, TransactionStatus> response = getTransactionStatus.execute(params);

    // then
    assertTrue(response.isRight());
    TransactionStatus transactionStatusResponse = response.get();
    assertEquals(reference, transactionStatusResponse.getReference());
    assertEquals(status, transactionStatusResponse.getStatus());
    assertEquals(amount, transactionStatusResponse.getAmount(), Double.NaN);
    assertEquals(fee, transactionStatusResponse.getFee(), Double.NaN);
  }

  @Test
  public void given_A_Reference_And_Client_And_Equal_Today_DateChannel_When_The_Get_Transaction_Status_Use_Case_Is_Executed_Then_The_Transaction_Should_Be_Returned() {
    // given
    String channel = "CLIENT";
    String reference = "11111A";
    long date = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
    double amount = 120.98;
    double fee = 2.00;
    String accountIban = "ES9820385778983000760236";
    String description = "Restaurant payment";
    String status = "PENDING";
    GetTransactionStatusParams params = new GetTransactionStatusParams(reference, channel);
    Transaction transaction = new Transaction(reference, accountIban, date, amount, fee, description);
    given(transactionCollection.findByReference(reference)).willReturn(of(transaction));

    // when
    Either<Error, TransactionStatus> response = getTransactionStatus.execute(params);

    // then
    double expectedAmount = amount - fee;
    assertTrue(response.isRight());
    TransactionStatus transactionStatusResponse = response.get();
    assertEquals(reference, transactionStatusResponse.getReference());
    assertEquals(status, transactionStatusResponse.getStatus());
    assertEquals(expectedAmount, transactionStatusResponse.getAmount(), Double.NaN);
  }

  @Test
  public void given_A_Reference_And_ATM_And_Equal_Today_DateChannel_When_The_Get_Transaction_Status_Use_Case_Is_Executed_Then_The_Transaction_Should_Be_Returned() {
    // given
    String channel = "CLIENT";
    String reference = "11111A";
    long date = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
    double amount = 120.98;
    double fee = 2.00;
    String accountIban = "ES9820385778983000760236";
    String description = "Restaurant payment";
    String status = "PENDING";
    GetTransactionStatusParams params = new GetTransactionStatusParams(reference, channel);
    Transaction transaction = new Transaction(reference, accountIban, date, amount, fee, description);
    given(transactionCollection.findByReference(reference)).willReturn(of(transaction));

    // when
    Either<Error, TransactionStatus> response = getTransactionStatus.execute(params);

    // then
    double expectedAmount = amount - fee;
    assertTrue(response.isRight());
    TransactionStatus transactionStatusResponse = response.get();
    assertEquals(reference, transactionStatusResponse.getReference());
    assertEquals(status, transactionStatusResponse.getStatus());
    assertEquals(expectedAmount, transactionStatusResponse.getAmount(), Double.NaN);
  }

  @Test
  public void given_A_Reference_And_Internal_And_Equal_Today_Date_Channel_When_The_Get_Transaction_Status_Use_Case_Is_Executed_Then_The_Transaction_Should_Be_Returned() {
    // given
    String channel = "INTERNAL";
    String reference = "11111A";
    long date = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
    double amount = 120.98;
    double fee = 2.00;
    String accountIban = "ES9820385778983000760236";
    String description = "Restaurant payment";
    String status = "PENDING";
    GetTransactionStatusParams params = new GetTransactionStatusParams(reference, channel);
    Transaction transaction = new Transaction(reference, accountIban, date, amount, fee, description);
    given(transactionCollection.findByReference(reference)).willReturn(of(transaction));

    // when
    Either<Error, TransactionStatus> response = getTransactionStatus.execute(params);

    // then
    assertTrue(response.isRight());
    TransactionStatus transactionStatusResponse = response.get();
    assertEquals(reference, transactionStatusResponse.getReference());
    assertEquals(status, transactionStatusResponse.getStatus());
    assertEquals(amount, transactionStatusResponse.getAmount(), Double.NaN);
    assertEquals(fee, transactionStatusResponse.getFee(), Double.NaN);
  }

  @Test
  public void given_A_Reference_And_Client_And_Greater_Today_DateChannel_When_The_Get_Transaction_Status_Use_Case_Is_Executed_Then_The_Transaction_Should_Be_Returned() {
    // given
    String channel = "CLIENT";
    String reference = "11111A";
    long date = LocalDateTime.now().plusDays(4).toInstant(ZoneOffset.UTC).toEpochMilli();
    double amount = 120.98;
    double fee = 2.00;
    String accountIban = "ES9820385778983000760236";
    String description = "Restaurant payment";
    String status = "FUTURE";
    GetTransactionStatusParams params = new GetTransactionStatusParams(reference, channel);
    Transaction transaction = new Transaction(reference, accountIban, date, amount, fee, description);
    given(transactionCollection.findByReference(reference)).willReturn(of(transaction));

    // when
    Either<Error, TransactionStatus> response = getTransactionStatus.execute(params);

    // then
    double expectedAmount = amount - fee;
    assertTrue(response.isRight());
    TransactionStatus transactionStatusResponse = response.get();
    assertEquals(reference, transactionStatusResponse.getReference());
    assertEquals(status, transactionStatusResponse.getStatus());
    assertEquals(expectedAmount, transactionStatusResponse.getAmount(), Double.NaN);
  }

  @Test
  public void given_A_Reference_And_ATM_Channel_And_Greater_Today_Date_When_The_Get_Transaction_Status_Use_Case_Is_Executed_Then_The_Transaction_Should_Be_Returned() {
    // given
    String channel = "ATM";
    String reference = "11111A";
    long date = LocalDateTime.now().plusDays(2).toInstant(ZoneOffset.UTC).toEpochMilli();
    double amount = 120.98;
    double fee = 2.00;
    String accountIban = "ES9820385778983000760236";
    String description = "Restaurant payment";
    String status = "PENDING";
    GetTransactionStatusParams params = new GetTransactionStatusParams(reference, channel);
    Transaction transaction = new Transaction(reference, accountIban, date, amount, fee, description);
    given(transactionCollection.findByReference(reference)).willReturn(of(transaction));

    // when
    Either<Error, TransactionStatus> response = getTransactionStatus.execute(params);

    // then
    double expectedAmount = amount - fee;
    assertTrue(response.isRight());
    TransactionStatus transactionStatusResponse = response.get();
    assertEquals(reference, transactionStatusResponse.getReference());
    assertEquals(status, transactionStatusResponse.getStatus());
    assertEquals(expectedAmount, transactionStatusResponse.getAmount(), Double.NaN);
  }
}
