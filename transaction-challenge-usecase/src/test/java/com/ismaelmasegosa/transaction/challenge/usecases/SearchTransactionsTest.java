package com.ismaelmasegosa.transaction.challenge.usecases;

import static com.ismaelmasegosa.transaction.challenge.usecases.mothers.TransactionMother.createTransactionsListAscendingOrderByAmount;
import static com.ismaelmasegosa.transaction.challenge.usecases.mothers.TransactionMother.createTransactionsListAscendingOrderByAmountAndFilterByIban;
import static com.ismaelmasegosa.transaction.challenge.usecases.mothers.TransactionMother.createTransactionsListDescendingOrderByAmount;
import static com.ismaelmasegosa.transaction.challenge.usecases.mothers.TransactionMother.createTransactionsListFilterByAccountIbanOrderByDate;
import static com.ismaelmasegosa.transaction.challenge.usecases.mothers.TransactionMother.createTransactionsListOrderByDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.usecases.params.SearchTransactionsParams;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class SearchTransactionsTest {

  TransactionCollection transactionCollection = mock(TransactionCollection.class);

  SearchTransactions searchTransactions = new SearchTransactions(transactionCollection);

  @Test
  public void given_A_Empty_Account_IBAN_And_Empty_Sort_When_The_Search_Transactions_Use_Case_Is_Executed_Then_The_Transactions_Should_Be_Returned() {
    // given
    SearchTransactionsParams params = new SearchTransactionsParams("", "");
    List<Transaction> transactions = createTransactionsListOrderByDate();
    given(transactionCollection.findByAccountIban("")).willReturn(transactions);

    // when
    Either<Error, List<Transaction>> response = searchTransactions.execute(params);

    // then
    assertTrue(response.isRight());
    List<Transaction> transactionsResponse = new ArrayList<>(response.get());
    assertEquals(4, transactionsResponse.size());
    assertEquals(transactionsResponse.get(0).getReference(), "44444A");
    assertEquals(transactionsResponse.get(1).getReference(), "33333A");
    assertEquals(transactionsResponse.get(2).getReference(), "22222A");
    assertEquals(transactionsResponse.get(3).getReference(), "11111A");
  }

  @Test
  public void given_A_Account_IBAN_When_The_Search_Transactions_Use_Case_Is_Executed_Then_The_Transactions_With_The_Account_IBAN_Should_Be_Returned() {
    // given
    String accountIban = "ES9820385778983000760236";
    SearchTransactionsParams params = new SearchTransactionsParams(accountIban, "");
    List<Transaction> transactions = createTransactionsListFilterByAccountIbanOrderByDate(accountIban);
    given(transactionCollection.findByAccountIban(accountIban)).willReturn(transactions);

    // when
    Either<Error, List<Transaction>> response = searchTransactions.execute(params);

    // then
    assertTrue(response.isRight());
    List<Transaction> transactionsResponse = new ArrayList<>(response.get());
    assertEquals(3, transactionsResponse.size());
    assertEquals(transactionsResponse.get(0).getReference(), "44444A");
    assertEquals(transactionsResponse.get(1).getReference(), "33333A");
    assertEquals(transactionsResponse.get(2).getReference(), "11111A");
  }

  @Test
  public void given_A_Ascending_Sort_When_The_Search_Transactions_Use_Case_Is_Executed_Then_The_Transactions_Should_Be_Returned_In_Ascending_Order() {
    // given
    String sort = "ascending";
    SearchTransactionsParams params = new SearchTransactionsParams("", sort);
    List<Transaction> transactions = createTransactionsListAscendingOrderByAmount();
    given(transactionCollection.findOrderByAmount(sort)).willReturn(transactions);

    // when
    Either<Error, List<Transaction>> response = searchTransactions.execute(params);

    // then
    assertTrue(response.isRight());
    List<Transaction> transactionsResponse = new ArrayList<>(response.get());
    assertEquals(4, transactionsResponse.size());
    assertEquals(transactionsResponse.get(0).getReference(), "22222A");
    assertEquals(transactionsResponse.get(1).getReference(), "11111A");
    assertEquals(transactionsResponse.get(2).getReference(), "44444A");
    assertEquals(transactionsResponse.get(3).getReference(), "33333A");
  }

  @Test
  public void given_A_Descending_Sort_When_The_Search_Transactions_Use_Case_Is_Executed_Then_The_Transactions_Should_Be_Returned_In_Descending_Order() {
    // given
    String sort = "descending";
    SearchTransactionsParams params = new SearchTransactionsParams("", sort);
    List<Transaction> transactions = createTransactionsListDescendingOrderByAmount();
    given(transactionCollection.findOrderByAmount(sort)).willReturn(transactions);

    // when
    Either<Error, List<Transaction>> response = searchTransactions.execute(params);

    // then
    assertTrue(response.isRight());
    List<Transaction> transactionsResponse = new ArrayList<>(response.get());
    assertEquals(4, transactionsResponse.size());
    assertEquals(transactionsResponse.get(0).getReference(), "33333A");
    assertEquals(transactionsResponse.get(1).getReference(), "44444A");
    assertEquals(transactionsResponse.get(2).getReference(), "11111A");
    assertEquals(transactionsResponse.get(3).getReference(), "22222A");
  }

  @Test
  public void given_A_Account_IBAN_A_Descending_Sort_When_The_Search_Transactions_Use_Case_Is_Executed_Then_The_Transactions_Should_Be_Returned_In_Descending_Order() {
    // given
    String accountIban = "ES9820385778983000760236";
    String sort = "ascending";
    SearchTransactionsParams params = new SearchTransactionsParams(accountIban, sort);
    List<Transaction> transactions = createTransactionsListAscendingOrderByAmountAndFilterByIban(accountIban);
    given(transactionCollection.findByAccountIbanOrderByAmount(accountIban, sort)).willReturn(transactions);

    // when
    Either<Error, List<Transaction>> response = searchTransactions.execute(params);

    // then
    assertTrue(response.isRight());
    List<Transaction> transactionsResponse = new ArrayList<>(response.get());
    assertEquals(3, transactionsResponse.size());
    assertEquals(transactionsResponse.get(0).getReference(), "11111A");
    assertEquals(transactionsResponse.get(1).getReference(), "44444A");
    assertEquals(transactionsResponse.get(2).getReference(), "33333A");
  }
}
