package com.ismaelmasegosa.transaction.challenge.usecases;

import static com.ismaelmasegosa.transaction.challenge.usecases.mothers.TransactionMother.createdTransationsList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Validation;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.usecases.params.SearchTransactionsParams;
import java.util.List;
import org.junit.Test;

public class SearchTransactionsTest {

  TransactionCollection transactionCollection = mock(TransactionCollection.class);

  SearchTransactions searchTransactions = new SearchTransactions();

  @Test
  public void given_A_Account_IBAN_And_Ascending_Sort_When_The_Search_Transactions_Use_Case_Is_Executed_Then_The_Transactions_Should_Be_Returned() {
    // given
    String accountIban = "ES9820385778983000760236";
    int sort = 1;
    SearchTransactionsParams params = new SearchTransactionsParams(accountIban, sort);
    List<Transaction> transactions = createdTransationsList();
    given(transactionCollection.findByAccountIbanOrderByAmount(accountIban, sort)).willReturn(transactions);

    // when
    Either<Validation, List<Transaction>> response = searchTransactions.execute(params);

    // then
    assertTrue(response.isRight());
    List<Transaction> transactionsResponse = response.get();
    assertEquals(4, transactionsResponse.size());
    assertEquals(transactionsResponse.get(0).getReference(), "33333A");
    assertEquals(transactionsResponse.get(1).getReference(), "44444A");
    assertEquals(transactionsResponse.get(2).getReference(), "11111A");
    assertEquals(transactionsResponse.get(3).getReference(), "22222A");
  }
}
