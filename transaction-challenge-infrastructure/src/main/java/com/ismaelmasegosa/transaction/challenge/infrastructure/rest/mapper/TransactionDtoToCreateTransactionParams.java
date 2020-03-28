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
    return new CreateTransactionParams(dto.getReference(), dto.getAccountIban(), dto.getDate().toInstant(ZoneOffset.UTC).toEpochMilli(),
        dto.getAmount(), dto.getFee(), dto.getDescription());
  }
}
