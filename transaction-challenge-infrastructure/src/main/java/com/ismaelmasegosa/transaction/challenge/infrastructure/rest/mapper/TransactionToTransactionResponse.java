package com.ismaelmasegosa.transaction.challenge.infrastructure.rest.mapper;

import static java.time.Instant.ofEpochMilli;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.infrastructure.rest.dto.TransactionResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class TransactionToTransactionResponse implements Function<Transaction, TransactionResponse> {

  @Override
  public TransactionResponse apply(Transaction transaction) {
    return TransactionResponse.builder().reference(transaction.getReference()).accountIban(transaction.getAccountIban())
        .amount(transaction.getAmount()).date(LocalDateTime.ofInstant(ofEpochMilli(transaction.getDate()), ZoneId.of("UTC")))
        .fee(transaction.getFee()).description(transaction.getDescription()).build();
  }
}
