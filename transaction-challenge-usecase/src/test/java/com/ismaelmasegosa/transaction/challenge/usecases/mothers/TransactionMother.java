package com.ismaelmasegosa.transaction.challenge.usecases.mothers;

import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;
import static java.util.Comparator.comparingLong;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionMother {

  public static Transaction createTransaction(String reference, String accountIban, long date, double amount, double fee,
      String description) {
    return new Transaction(reference, accountIban, date, amount, fee, description);
  }

  public static List<Transaction> createTransactionsList() {
    return asList(createTransaction("11111A", "ES9820385778983000760236", getLocalDatePlusDays(4), 120.98, 2.00, "Restaurant Payment"),
        createTransaction("22222A", "ES9820385778983000760236", getLocalDatePlusDays(3), 39.90, 2.00, "Parking Payment"),
        createTransaction("33333A", "ES9820385778983000760236", getLocalDatePlusDays(2), 1067.90, 2.00, "Car Payment"),
        createTransaction("44444A", "ES9820385778983000760236", getLocalDatePlusDays(1), 908.90, 2.00, "House Payment"));
  }

  public static List<Transaction> createTransactionsListOrderByDate() {
    List<Transaction> transactionsList = createTransactionsList();
    return transactionsList.stream().sorted(comparingLong(Transaction::getDate).reversed()).collect(Collectors.toList());
  }

  private static long getLocalDatePlusDays(long days) {
    return now().minusDays(days).toInstant(ZoneOffset.UTC).toEpochMilli();
  }
}