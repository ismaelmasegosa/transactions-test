package com.ismaelmasegosa.transaction.challenge.acceptance.config;

import static com.ismaelmasegosa.transaction.challenge.acceptance.config.mothers.TransactionEntityMother.createTransactionEntity;
import static java.time.LocalDateTime.now;

import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.TransactionRepository;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Autowired;

public class Hooks {

  @Autowired
  TransactionRepository transactionRepository;

  @Before("@Create")
  public void createTransactions() {
    transactionRepository
        .save(createTransactionEntity("11111A", "ES9820385778983000760236", getLocalDateMinusDays(4), 120.98, 2.00, "Restaurant Payment"));
    transactionRepository
        .save(createTransactionEntity("22222A", "ES9820385778983000732453", getLocalDateMinusDays(3), 39.90, 2.00, "Parking Payment"));
    transactionRepository
        .save(createTransactionEntity("33333A", "ES9820385778983000760236", getLocalDateMinusDays(2), 1067.90, 2.00, "Car Payment"));
    transactionRepository
        .save(createTransactionEntity("44444A", "ES9820385778983000760236", getLocalDateMinusDays(1), 908.90, 2.00, "House Payment"));
  }

  @After("@Remove")
  public void deleteTransactions() {
    transactionRepository.delete("11111A");
    transactionRepository.delete("22222A");
    transactionRepository.delete("33333A");
    transactionRepository.delete("44444A");
  }

  @Before("@CreateTransactionBeforeToday")
  public void createTransactionBeforeToday() {
    transactionRepository
        .save(createTransactionEntity("11111A", "ES9820385778983000760236", getLocalDateMinusDays(4), 120.98, 2.00, "Restaurant Payment"));
  }

  @After("@RemoveTransactionsBeforeToday")
  public void deleteTransactionsBeforeToday() {
    transactionRepository.delete("11111A");
  }

  private long getLocalDateMinusDays(long days) {
    return now().minusDays(days).toInstant(ZoneOffset.UTC).toEpochMilli();
  }
}
