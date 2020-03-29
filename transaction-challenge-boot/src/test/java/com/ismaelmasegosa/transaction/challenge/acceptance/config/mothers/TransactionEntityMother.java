package com.ismaelmasegosa.transaction.challenge.acceptance.config.mothers;

import static java.time.Instant.now;

import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;

public class TransactionEntityMother {

  public static TransactionEntity createTransactionEntity(String reference, String accountIban, double amount, double fee,
      String description) {
    return new TransactionEntity(reference, accountIban, now().toEpochMilli(), amount, fee, description);
  }
}
