package com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.filters;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Filter {

  private String accountIban;

  private String sort;
}
