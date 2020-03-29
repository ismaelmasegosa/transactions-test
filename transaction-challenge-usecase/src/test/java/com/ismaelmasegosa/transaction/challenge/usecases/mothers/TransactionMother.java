package com.ismaelmasegosa.transaction.challenge.usecases.mothers;

import static java.time.Instant.now;
import static java.util.Arrays.asList;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import java.util.List;

public class TransactionMother {

  public static Transaction createTransaction(String reference, String accountIban, double amount, double fee, String description) {
    return new Transaction(reference, accountIban, now().toEpochMilli(), amount, fee, description);
  }

  public static List<Transaction> createdTransationsList() {
    return asList(createTransaction("11111A", "ES9820385778983000760236", 120.98, 2.00, "Restaurant Payment"),
        createTransaction("22222A", "ES9820385778983000760236", 39.90, 2.00, "Parking Payment"),
        createTransaction("33333A", "ES9820385778983000760236", 1067.90, 2.00, "Car Payment"),
        createTransaction("44444A", "ES9820385778983000760236", 908.90, 2.00, "House Payment"));
  }
}
