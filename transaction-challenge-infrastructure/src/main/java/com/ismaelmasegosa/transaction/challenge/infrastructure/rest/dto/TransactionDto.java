package com.ismaelmasegosa.transaction.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionDto {

  private String reference;

  @JsonProperty("account_iban")
  private String accountIban;

  private LocalDateTime date;

  private double amount;

  private double fee;

  private String description;
}
