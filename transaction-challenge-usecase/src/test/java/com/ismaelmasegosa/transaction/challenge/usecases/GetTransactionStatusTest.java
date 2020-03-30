package com.ismaelmasegosa.transaction.challenge.usecases;

import static java.util.Optional.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.core.TransactionError;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.usecases.params.GetTransactionStatusParams;
import org.junit.Test;

public class GetTransactionStatusTest {

  TransactionCollection transactionCollection = mock(TransactionCollection.class);

  GetTransactionStatus getTransactionStatus = new GetTransactionStatus();

  @Test
  public void given_A_Invalid_Reference_When_The_Get_Transaction_Status_Use_Case_Is_Executed_Then_The_Transactions_Error_Should_Be_Returned() {
    // given
    String channel = "INTERNAL";
    String reference = "211223A";
    GetTransactionStatusParams params = new GetTransactionStatusParams(reference, channel);
    given(transactionCollection.findByReference(reference)).willReturn(empty());

    // when
    Either<Error, Transaction> response = getTransactionStatus.execute(params);

    // then
    assertTrue(response.isLeft());
    Error error = response.getLeft();
    TransactionError transactionError = error.getTransactionError();
    assertEquals(404, error.getStatusCode());
    assertEquals(reference, transactionError.getReference());
    assertEquals("INVALID", transactionError.getChannel());
  }
}
