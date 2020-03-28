package com.ismaelmasegosa.transaction.challenge.usecases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.usecases.params.CreateTransactionParams;
import org.junit.Test;

public class CreateTransactionTest {

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
    Transaction transaction = new Transaction(reference, accountIban, date, amount, fee, description);
    given(transactionCollection.addTransaction(any(Transaction.class))).willReturn(transaction);
    CreateTransactionParams params = new CreateTransactionParams(reference, accountIban, date, amount, fee, description);

    // when
    Either<Error, Transaction> response = createTransaction.execute(params);

    // then
    assertTrue(response.isRight());
    Transaction transactionResponse = response.get();
    assertEquals(reference, transactionResponse.getReference());
    assertEquals(accountIban, transactionResponse.getAccountIban());
    assertEquals(amount, transactionResponse.getAmount(), Double.POSITIVE_INFINITY);
    assertEquals(fee, transactionResponse.getFee(), Double.POSITIVE_INFINITY);
    assertEquals(date, transactionResponse.getDate());
    assertEquals(description, transactionResponse.getDescription());
  }
}
