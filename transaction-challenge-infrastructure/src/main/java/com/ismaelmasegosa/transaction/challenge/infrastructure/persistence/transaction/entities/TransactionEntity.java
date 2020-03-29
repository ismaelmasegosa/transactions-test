package com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TransactionEntity {

  private String reference;

  private String accountIban;

  private long date;

  private double amount;

  private double fee;

  private String description;
}
