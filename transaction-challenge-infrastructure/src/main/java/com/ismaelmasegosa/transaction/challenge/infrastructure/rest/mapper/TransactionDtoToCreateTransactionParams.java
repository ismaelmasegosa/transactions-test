package com.ismaelmasegosa.transaction.challenge.infrastructure.rest.mapper;

import com.ismaelmasegosa.transaction.challenge.infrastructure.rest.dto.TransactionDto;
import com.ismaelmasegosa.transaction.challenge.usecases.params.CreateTransactionParams;
import java.time.ZoneOffset;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoToCreateTransactionParams implements Function<TransactionDto, CreateTransactionParams> {

  @Override
  public CreateTransactionParams apply(TransactionDto dto) {
    long date = dto.getDate() != null ? dto.getDate().toInstant(ZoneOffset.UTC).toEpochMilli() : System.currentTimeMillis();
    return new CreateTransactionParams(dto.getReference(), dto.getAccountIban(), date, dto.getAmount(), dto.getFee(), dto.getDescription());
  }
}
