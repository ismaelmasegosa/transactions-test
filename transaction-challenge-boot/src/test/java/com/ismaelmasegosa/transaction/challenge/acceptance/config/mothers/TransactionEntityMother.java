package com.ismaelmasegosa.transaction.challenge.acceptance.config.mothers;

import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;

public class TransactionEntityMother {

  public static TransactionEntity createTransactionEntity(String reference, String accountIban, long date, double amount, double fee,
      String description) {
    return new TransactionEntity(reference, accountIban, date, amount, fee, description);
  }
}
