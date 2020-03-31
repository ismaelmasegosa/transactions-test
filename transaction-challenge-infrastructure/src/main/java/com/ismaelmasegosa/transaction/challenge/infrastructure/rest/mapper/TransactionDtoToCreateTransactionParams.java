package com.ismaelmasegosa.transaction.challenge.infrastructure.rest.mapper;

import static java.math.RoundingMode.HALF_EVEN;

import com.ismaelmasegosa.transaction.challenge.infrastructure.rest.dto.TransactionDto;
import com.ismaelmasegosa.transaction.challenge.usecases.params.CreateTransactionParams;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoToCreateTransactionParams implements Function<TransactionDto, CreateTransactionParams> {

  @Override
  public CreateTransactionParams apply(TransactionDto dto) {
    long date = dto.getDate() != null ? dto.getDate().toInstant(ZoneOffset.UTC).toEpochMilli() : Instant.now().toEpochMilli();
    return new CreateTransactionParams(dto.getReference(), dto.getAccountIban(), date, roundToTwoDecimals(dto.getAmount()), dto.getFee(),
        dto.getDescription());
  }

  private double roundToTwoDecimals(double amount) {
    return new BigDecimal(amount).setScale(2, HALF_EVEN).doubleValue();
  }
}
