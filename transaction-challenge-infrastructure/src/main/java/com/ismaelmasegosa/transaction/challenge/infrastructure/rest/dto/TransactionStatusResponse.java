package com.ismaelmasegosa.transaction.challenge.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class TransactionStatusResponse {

  private String reference;

  private String status;

  private Double amount;

  private Double fee;
}
