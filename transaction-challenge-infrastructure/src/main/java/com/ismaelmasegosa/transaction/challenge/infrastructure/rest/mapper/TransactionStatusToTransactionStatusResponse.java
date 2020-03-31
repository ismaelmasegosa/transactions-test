package com.ismaelmasegosa.transaction.challenge.infrastructure.rest.mapper;

import static java.math.RoundingMode.HALF_EVEN;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.TransactionStatus;
import com.ismaelmasegosa.transaction.challenge.infrastructure.rest.dto.TransactionStatusResponse;
import java.math.BigDecimal;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class TransactionStatusToTransactionStatusResponse implements Function<TransactionStatus, TransactionStatusResponse> {

  @Override
  public TransactionStatusResponse apply(TransactionStatus transactionStatus) {
    return TransactionStatusResponse.builder().reference(transactionStatus.getReference())
        .amount(isNullValue(transactionStatus.getAmount())).fee(isNullValue(transactionStatus.getFee()))
        .status(transactionStatus.getStatus()).build();
  }

  private Double isNullValue(double amount) {
    return amount != 0.0 ? roundToTwoDecimals(amount) : null;
  }

  private double roundToTwoDecimals(double amount) {
    return new BigDecimal(amount).setScale(2, HALF_EVEN).doubleValue();
  }

}
